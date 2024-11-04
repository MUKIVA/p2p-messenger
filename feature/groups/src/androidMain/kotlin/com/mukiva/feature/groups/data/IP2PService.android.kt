package com.mukiva.feature.groups.data

import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual class P2PService(
    private val managerFactory: WifiP2PManagerFactory,
    private val commonNotifier: CommonNotifier = CommonNotifier()
) : IP2PService, ICommonNotifier by commonNotifier {

    private var manager: WifiP2pManager? = null
    private var channel: WifiP2pManager.Channel? = null
    private var receiver: P2PReceiver? = null

    private var mConnectionHolder: IConnectionHolder? = null

    private val receiverCallbacks = object : P2PReceiver.Callbacks {
        override val onP2PStateChanged: (isEnabled: Boolean) -> Unit
            get() = commonNotifier::notifyWifiStateChanged
        override val onPeerListChanged: (Collection<WifiP2pDevice>) -> Unit
            get() = commonNotifier::notifyPeerListChanged
        override val onConnectionSuccess: (info: WifiP2pInfo?) -> Unit
            get() = ::handleConnectionSuccess
        override val onConnectionLost: () -> Unit
            get() = ::handleConnectionLost
        override val onThisDeviceChanged: (WifiP2pDevice?) -> Unit
            get() = {
                // Nothing to do
            }
    }

    override fun discoverPeers(
        onSuccess: () -> Unit,
        onFailure: (IP2PService.DiscoveryError) -> Unit
    ) {
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                onSuccess()
            }

            override fun onFailure(reason: Int) {
                onFailure(mapReasonToCommon(reason))
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

        manager?.requestGroupInfo(channel) { info ->
            println()
        }

        manager?.connect(channel, config, object : WifiP2pManager.ActionListener{
            override fun onSuccess() {
                Log.d("P2PService", "Connection success")

                onSuccess()
            }

            override fun onFailure(reason: Int) {
                Log.d("P2PService", "Connection Fail")
                onFailure(mapReasonToCommon(reason))
            }
        })
    }

    override fun sendMessage(message: String) {
        mConnectionHolder?.sendMessage(message)
    }

    override fun closeConnection() {
        mConnectionHolder?.cancel()
    }

    fun onResume(activity: ComponentActivity) = with(activity) {
        receiver?.also { receiver ->
            registerReceiver(
                receiver,
                P2PReceiver.intentFilter,
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
            receiver = P2PReceiver(
                manager = manager!!,
                channel = channel,
                callbacks = receiverCallbacks
            )
        }
    }

    private fun mapReasonToCommon(reason: Int) = when (reason) {
        WifiP2pManager.BUSY -> IP2PService.DiscoveryError.BUSY
        WifiP2pManager.ERROR -> IP2PService.DiscoveryError.ERROR
        else -> IP2PService.DiscoveryError.UNSUPPORTED
    }

    private fun handleConnectionLost() {
        closeConnection()
        commonNotifier
            .notifyConnectionStatusChanged(IConnectionStatus.Lost)
    }

    private fun handleConnectionSuccess(info: WifiP2pInfo?) {
        val groupOwnerAddress = info?.groupOwnerAddress
            ?: return

        Log.d("ConnectionSuccess", "$groupOwnerAddress")

        if (mConnectionHolder != null) {
            return
        }

        if (info.groupFormed && info.isGroupOwner) {
            // Телефон выступает в роли хоста
            mConnectionHolder = MessagingServer(
                hostAddress = groupOwnerAddress,
                onMessageReceived = commonNotifier::notifyNewMessage
            )
            mConnectionHolder?.start()
        } else if (info.groupFormed) {
            // Телефон выступает в роли клиента
            mConnectionHolder = MessagingClient(
                hostAddress = groupOwnerAddress.hostAddress ?: "localhost",
                onMessageReceived = commonNotifier::notifyNewMessage
            )
            mConnectionHolder?.start()
        }

        commonNotifier.notifyConnectionStatusChanged(IConnectionStatus.Success)
    }
}