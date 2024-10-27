package com.mukiva.feature.groups.data

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

    interface IDiscoverPeersCallback {
        fun onSuccess()
        fun onFailure(reason: DiscoveryError)
    }

    interface IPeerListChangedCallback {
        fun onPeerListChanged(list: List<PeerInfo>)
    }

    fun addListener(listener: IPeerListChangedCallback)
    fun addMessageListener(listener: (String) -> Unit)
    fun addOnClientCreatedListener(listener: () -> Unit)
    fun discoverPeers(callbacks: IDiscoverPeersCallback)
    fun connect(
        device: PeerInfo,
        onSuccess: () -> Unit,
        onFailure: (DiscoveryError) -> Unit
    )
    suspend fun sendMessage(message: String)
}

expect class P2PService : IP2PService