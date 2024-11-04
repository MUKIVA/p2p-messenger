package com.mukiva.feature.groups.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

class P2PReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val callbacks: Callbacks
) : BroadcastReceiver() {

    interface Callbacks {
        val onP2PStateChanged: (isEnabled: Boolean) -> Unit

        val onPeerListChanged: (Collection<WifiP2pDevice>) -> Unit

        val onConnectionSuccess: (info: WifiP2pInfo?) -> Unit
        val onConnectionLost: () -> Unit

        val onThisDeviceChanged: (WifiP2pDevice?) -> Unit
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_STATE_CHANGED_ACTION")
                handleWifiP2pStateChanged(intent)
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_PEERS_CHANGED_ACTION")
                handleWifiP2pPeersChanged()
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_CONNECTION_CHANGED_ACTION")
                handleWifiP2pConnectionChanged(intent)
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION")
                handleWifiP2pThisDeviceChanged(intent)
            }
        }
    }

    private val mPeerListListener = object : WifiP2pManager.PeerListListener {
        override fun onPeersAvailable(peers: WifiP2pDeviceList?) {
            callbacks.onPeerListChanged(peers?.deviceList ?: emptyList())
        }
    }

    private val mConnectionListener = object : WifiP2pManager.ConnectionInfoListener {
        override fun onConnectionInfoAvailable(info: WifiP2pInfo?) {
            callbacks.onConnectionSuccess(info)
        }
    }

    private fun handleWifiP2pStateChanged(intent: Intent) {
        val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
        callbacks.onP2PStateChanged(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
    }

    private fun handleWifiP2pPeersChanged() {
        manager.requestPeers(channel, mPeerListListener)
    }

    private fun handleWifiP2pConnectionChanged(intent: Intent) {
        val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
        if (networkInfo != null && networkInfo.isConnected) {
            Log.d("P2PReceiver", "Connection Create")
            manager.requestConnectionInfo(channel, mConnectionListener)
        } else {
            Log.d("P2PReceiver", "Connection Lost")
            callbacks.onConnectionLost()
        }
    }

    private fun handleWifiP2pThisDeviceChanged(intent: Intent) {
        val device = intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)
        callbacks.onThisDeviceChanged(device)
    }

    companion object {
        val intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
    }

}