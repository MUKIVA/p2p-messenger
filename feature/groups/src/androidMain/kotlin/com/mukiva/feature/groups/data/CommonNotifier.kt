package com.mukiva.feature.groups.data

import android.net.wifi.p2p.WifiP2pDevice
import android.util.Log
import com.mukiva.feature.groups.data.IP2PService.PeerInfo

class CommonNotifier : ICommonNotifier {

    private val mDataSetListeners = mutableListOf<(List<PeerInfo>) -> Unit>()
    private val mWifiStateListeners = mutableListOf<(Boolean) -> Unit>()
    private val mConnectionStatusListeners = mutableListOf<(IConnectionStatus) -> Unit>()
    private val mMessageListeners = mutableListOf<(String) -> Unit>()

    override fun addPeerListListener(listener: (List<PeerInfo>) -> Unit) {
        mDataSetListeners.add(listener)
    }

    override fun addWifiStateListener(listener: (Boolean) -> Unit) {
        mWifiStateListeners.add(listener)
    }

    override fun addConnectionStatusListener(listener: (IConnectionStatus) -> Unit) {
        mConnectionStatusListeners.add(listener)
    }

    override fun addMessageListener(listener: (String) -> Unit) {
        mMessageListeners.add(listener)
    }

    fun notifyPeerListChanged(dataSet: Collection<WifiP2pDevice>) {
        mDataSetListeners.onEach { listener ->
            val commonList = dataSet.map(::mapToCommon)
            listener(commonList)
        }
    }

    fun notifyWifiStateChanged(isEnabled: Boolean) {
        mWifiStateListeners.onEach { listener ->
            listener(isEnabled)
        }
    }

    fun notifyConnectionStatusChanged(status: IConnectionStatus) {
        mConnectionStatusListeners.onEach { listener ->
            listener(status)
        }
    }

    fun notifyNewMessage(msg: String) {
        mMessageListeners.onEach { listener ->
            listener.invoke(msg)
        }
    }

    private fun mapToCommon(item: WifiP2pDevice) = PeerInfo(
        deviceName = item.deviceName + " | " + item.deviceAddress,
        deviceAddress = item.deviceAddress
    ).also { device ->
        Log.d("CommonNotifier", "$device")
    }
}