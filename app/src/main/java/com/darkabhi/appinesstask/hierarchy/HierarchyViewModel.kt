package com.darkabhi.appinesstask.hierarchy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkabhi.appinesstask.common.ResultWrapper
import com.darkabhi.appinesstask.common.State
import com.darkabhi.appinesstask.hierarchy.data.repo.MockableRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HierarchyViewModel @Inject constructor(private val mockableRepoImpl: MockableRepoImpl) :
    ViewModel() {

    private val _mockableResponse = Channel<State>(Channel.BUFFERED)
    val mockableResponse = _mockableResponse.receiveAsFlow()

    init {
        getMockableData()
    }

    fun getMockableData() =
        viewModelScope.launch {
            _mockableResponse.send(State.Loading)
            when (
                val response =
                    mockableRepoImpl.getMockableData()
            ) {
                is ResultWrapper.GenericError -> {
                    _mockableResponse.send(State.Failed(response.error?.error))
                }
                ResultWrapper.NetworkError -> {
                    _mockableResponse.send(State.Failed("Network Error"))
                }
                is ResultWrapper.Success -> {
                    _mockableResponse.send(State.Success(response.value))
                }
            }
        }
}