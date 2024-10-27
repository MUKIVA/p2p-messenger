package com.mukiva.feature.groups.data

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager

class WifiP2PManagerFactory(
    private val context: Context
) {

    fun create(): WifiP2pManager {
        return context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    }

}