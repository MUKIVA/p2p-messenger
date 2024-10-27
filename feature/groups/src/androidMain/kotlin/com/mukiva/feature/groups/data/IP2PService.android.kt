package com.mukiva.feature.groups.data

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import androidx.activity.ComponentActivity
import java.net.Socket

actual class P2PService(
    private val managerFactory: WifiP2PManagerFactory
) : IP2PService {

    private val mPeers = mutableListOf<WifiP2pDevice>()
    private val mDataSetChangedListeners = mutableListOf<IP2PService.IPeerListChangedCallback>()
    private val mMessageListeners = mutableListOf<(String) -> Unit>()
    private val mOnClientCreated = mutableListOf<() -> Unit>()

    private var manager: WifiP2pManager? = null
    private var channel: WifiP2pManager.Channel? = null
    private var receiver: BroadcastReceiver? = null

    private var serverSideThread: ServerSideThread? = null
    private var clientSideThread: ClientSideThread? = null
    private var messageSender: MessageSender? = null

    private val handler = RecievMessageHandler(
        onMessageReceived = ::notifyMessageReceived
    )

    private val mPeerListener = WifiP2pManager.PeerListListener { peers ->  
        val refreshedPeers = peers.deviceList
        if (refreshedPeers != peers) {
            mPeers.clear()
            mPeers.addAll(refreshedPeers)
        }

        notifyDataSetChanged()
        Log.d("P2PService", "Data set changed")
    }

    private val mConnectionListener = WifiP2pManager.ConnectionInfoListener { info ->

        val groupOwnerAddress = info.groupOwnerAddress

        if (info.groupFormed && info.isGroupOwner) {
            serverSideThread = ServerSideThread(
                onSocketCreated = { socket: Socket ->
                    messageSender = MessageSender(socket, handler)
                }
            )
            serverSideThread?.start()
        } else if (info.groupFormed) {
            clientSideThread = ClientSideThread(
                groupOwnerAddress,
                onSocketCreated = { socket ->
                    messageSender = MessageSender(socket, handler)
                }
            )
            clientSideThread?.start()
        }

        mOnClientCreated.onEach { listener ->
            listener.invoke()
        }
    }

    override fun discoverPeers(callbacks: IP2PService.IDiscoverPeersCallback) {
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                callbacks.onSuccess()
            }

            override fun onFailure(reason: Int) {
                callbacks.onFailure(mapReasonToCommon(reason))
            }
        })
    }

    override fun connect(
        device: IP2PService.PeerInfo,
        onSuccess: () -> Unit,
        onFailure: (IP2PService.DiscoveryError) -> Unit
    ) {
        val config = WifiP2pConfig().apply {
            deviceAddress = device.deviceAddress
            wps.setup = WpsInfo.PBC
        }

        manager?.connect(channel, config, object : WifiP2pManager.ActionListener{
            override fun onSuccess() {
                onSuccess()
            }

            override fun onFailure(reason: Int) {
                onFailure(mapReasonToCommon(reason))
            }

        })
    }

    override suspend fun sendMessage(message: String) {
        messageSender?.write(message.toByteArray())
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun onResume(activity: ComponentActivity) = with(activity) {
        receiver?.also { receiver ->
            registerReceiver(
                receiver,
                P2PReceiver.intentFilter
            )
        }
    }

    fun onPause(activity: ComponentActivity) = with(activity) {
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }

    fun setupNetworkConfiguration(activity: ComponentActivity) = with(activity) {
        manager = managerFactory.create()
        channel = manager?.initialize(this, mainLooper, null)
        channel?.also { channel ->
            receiver = P2PReceiver(manager!!, channel, mPeerListener, mConnectionListener)
        }
    }

    override fun addListener(listener: IP2PService.IPeerListChangedCallback) {
        mDataSetChangedListeners.add(listener)
    }

    override fun addMessageListener(listener: (String) -> Unit) {
        mMessageListeners.add(listener)
    }

    override fun addOnClientCreatedListener(listener: () -> Unit) {
        mOnClientCreated.add(listener)
    }

    private fun notifyDataSetChanged() {
        mDataSetChangedListeners.onEach { listener ->
            val commonList = mPeers.map(::mapToCommon)
            listener.onPeerListChanged(commonList)
        }
    }

    private fun mapToCommon(item: WifiP2pDevice) = IP2PService.PeerInfo(
        deviceName = item.deviceName,
        deviceAddress = item.deviceAddress
    )

    private fun mapReasonToCommon(reason: Int) = when (reason) {
        WifiP2pManager.BUSY -> IP2PService.DiscoveryError.BUSY
        WifiP2pManager.ERROR -> IP2PService.DiscoveryError.ERROR
        else -> IP2PService.DiscoveryError.UNSUPPORTED
    }

    private fun notifyMessageReceived(msg: String) {
        mMessageListeners.onEach { listener ->
            listener.invoke(msg)
        }
    }
}