package com.mukiva.p2pmessenger.main.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.navigation.IDestination
import com.mukiva.p2pmessenger.main.navigation.NavigationStub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    val state: StateFlow<IDestination>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow<IDestination>(NavigationStub)



}