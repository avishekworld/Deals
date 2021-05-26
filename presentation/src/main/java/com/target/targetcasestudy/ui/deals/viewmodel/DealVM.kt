package com.target.targetcasestudy.ui.deals.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.usecase.deals.GetDealsDetailsUseCase
import life.avishekworld.domain.usecase.deals.GetDealsUseCase

class DealVM(private val getDealsUseCase: GetDealsUseCase,
             private val getDealsDetailsUseCase: GetDealsDetailsUseCase) : ViewModel() {

    private val _internalState = MutableLiveData(InternalState(getDefaultViewState()))
    val viewState : LiveData<ViewState> = _internalState.map {
        it.viewState
    }
    private val state : InternalState
        get() = requireNotNull(_internalState.value)

    private val channel = Channel<DealsEvent>(Channel.UNLIMITED)

    init {
        channel
            .receiveAsFlow()
            .catch { Log.d(TAG, "channel event error $it") }
            .onEach { event ->
                Log.d(TAG, "event $event")
                when(event) {
                    is DealsEvent.DealsListViewInit -> handleDealsListViewInit(event)
                    is DealsEvent.DealDetailsViewInit -> handleDealsDetailsViewInit(event)
                }
            }
            .launchIn(viewModelScope)

    }

    private fun updateState(state : InternalState) {
        _internalState.value = state
    }

    private fun handleDealsListViewInit(event: DealsEvent.DealsListViewInit) {
        viewModelScope.launch {
            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Show)))

            val deals = getDealsUseCase.run()

            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Hide,
                    dealListViewState = DealListViewState.Show(deals))))
        }
    }

    private fun handleDealsDetailsViewInit(event: DealsEvent.DealDetailsViewInit) {
        viewModelScope.launch {
            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Show)))

            val product = getDealsDetailsUseCase.run(event.id)

            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Hide,
                    dealDetailsViewState = DealDetailsViewState.Show(product))))
        }
    }

    fun handleEvent(event : DealsEvent) {
        channel.offer(event)
    }

    private fun getDefaultViewState() : ViewState {
        return ViewState(
            ProcessingViewState.Hide,
            DealListViewState.Hide,
            DealDetailsViewState.Hide
        )
    }

    sealed class DealsEvent {
        object DealsListViewInit : DealsEvent()
        data class DealDetailsViewInit(val id : Int) : DealsEvent()
    }

    data class InternalState(val viewState: ViewState)

    data class ViewState(
        val processingViewState: ProcessingViewState,
        val dealListViewState: DealListViewState,
        val dealDetailsViewState: DealDetailsViewState,
    )

    sealed class ProcessingViewState {
        object Hide : ProcessingViewState()
        object Show : ProcessingViewState()
    }

    sealed class DealListViewState {
        object Hide : DealListViewState()
        data class Show(val deals: Deals) : DealListViewState()
    }

    sealed class DealDetailsViewState {
        object Hide : DealDetailsViewState()
        data class Show(val product: Product) : DealDetailsViewState()
    }

    companion object {
        const val TAG = "DealsVM"
    }
}