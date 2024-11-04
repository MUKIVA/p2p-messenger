package com.mukiva.feature.groups.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.core.navigation.INavHost
import com.mukiva.feature.groups.data.IConnectionStatus
import com.mukiva.feature.groups.data.IP2PService
import com.mukiva.feature.groups.data.P2PService
import com.mukiva.feature.groups.navigation.ChatDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface ISearchState {
    data object Init : ISearchState
    data object Error : ISearchState
    data object Loading : ISearchState
    data object Unsupported : ISearchState
    data class Content(
        val deviceList: List<IP2PService.PeerInfo>
    ) : ISearchState
}

internal class SearchViewModel(
    private val navHost: INavHost,
    private val p2pService: P2PService
) : ViewModel(), INavHost by navHost {

    val state: StateFlow<ISearchState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow<ISearchState>(ISearchState.Init)

    init {
        p2pService.addPeerListListener(::handleDeviceListUpdate)
        p2pService.addConnectionStatusListener(::handleConnectionStatusChange)
    }

    fun connect(device: IP2PService.PeerInfo) {
        p2pService.connect(
            device = device,
            onSuccess = { navigate(ChatDestination) },
            onFailure = {
                println("Connection Fail")
            }
        )
    }

    fun discoverPeers() {
        setStateLoading()
        p2pService.discoverPeers(
            onSuccess = {},
            onFailure = ::handleError
        )
    }

    private fun handleError(reason: IP2PService.DiscoveryError) {
        when (reason) {
            IP2PService.DiscoveryError.BUSY -> setStateLoading()
            IP2PService.DiscoveryError.UNSUPPORTED -> setStateUnsupported()
            IP2PService.DiscoveryError.ERROR -> setStateError()
        }
    }

    private fun setStateLoading() = mState.update {
        ISearchState.Loading
    }

    private fun setStateUnsupported() = mState.update {
        ISearchState.Unsupported
    }

    private fun setStateError() = mState.update {
        ISearchState.Error
    }

    private fun handleDeviceListUpdate(list: List<IP2PService.PeerInfo>) = mState.update {
        ISearchState.Content(
            deviceList = list
        )
    }

    private fun handleConnectionStatusChange(status: IConnectionStatus) {
        if (status == IConnectionStatus.Success) {
            navHost.navigate(ChatDestination)
        }
    }

}