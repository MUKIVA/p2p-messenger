package com.mukiva.feature.groups.data

import com.mukiva.feature.groups.data.IP2PService.PeerInfo

interface ICommonNotifier {
    fun addPeerListListener(listener: (List<PeerInfo>) -> Unit)
    fun addWifiStateListener(listener: (Boolean) -> Unit)
    fun addConnectionStatusListener(listener: (IConnectionStatus) -> Unit)
    fun addMessageListener(listener: (String) -> Unit)
}

sealed interface IConnectionStatus {
    data object Success : IConnectionStatus
    data object Lost : IConnectionStatus
}

interface IP2PService {

    enum class DiscoveryError {
        BUSY,
        UNSUPPORTED,
        ERROR
    }

    data class PeerInfo(
        val deviceName: String,
        val deviceAddress: String
    )
    
    fun discoverPeers(
        onSuccess: () -> Unit,
        onFailure: (DiscoveryError) -> Unit
    )
    fun connect(
        device: PeerInfo,
        onSuccess: () -> Unit,
        onFailure: (DiscoveryError) -> Unit
    )
    
    fun sendMessage(message: String)

    fun closeConnection()
}

expect class P2PService : IP2PService, ICommonNotifier