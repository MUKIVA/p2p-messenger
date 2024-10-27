package com.mukiva.feature.groups.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

@Suppress("DEPRECATION")
class P2PReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val listener: WifiP2pManager.PeerListListener,
    private val connectionListener: WifiP2pManager.ConnectionInfoListener
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_STATE_CHANGED_ACTION")
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_PEERS_CHANGED_ACTION")
                manager.requestPeers(channel, listener)
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_CONNECTION_CHANGED_ACTION")
                val networkInfo: NetworkInfo? = intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO)!!

                if (networkInfo?.isConnected == true) {
                    manager.requestConnectionInfo(channel, connectionListener)
                }
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                Log.d("P2PReceiver", "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION")
            }
        }
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