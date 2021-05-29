package com.target.targetcasestudy.ui.deals.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.target.targetcasestudy.navigation.NavEvent
import com.target.targetcasestudy.navigation.SingeEvent
import com.target.targetcasestudy.ui.deals.DealItemFragment
import com.target.targetcasestudy.ui.deals.DealListFragment
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.model.Result
import life.avishekworld.domain.usecase.deals.GetDealsDetailsUseCase
import life.avishekworld.domain.usecase.deals.GetDealsUseCase
import life.avishekworld.domain.util.CardValidator

class DealVM(private val getDealsUseCase: GetDealsUseCase,
             private val getDealsDetailsUseCase: GetDealsDetailsUseCase,
             private val cardValidator: CardValidator) : ViewModel() {

    private val _internalState = MutableLiveData(InternalState(getDefaultViewState()))
    val viewState : LiveData<ViewState> = _internalState.map {
        it.viewState
    }
    private val state : InternalState
        get() = requireNotNull(_internalState.value)

    val navigationEvent  = MutableLiveData<SingeEvent<NavEvent>>()

    private val channel = Channel<DealsEvent>(Channel.UNLIMITED)

    init {
        channel
            .receiveAsFlow()
            .catch { Log.d(TAG, "channel event error $it") }
            .onEach { event ->
                Log.d(TAG, "event $event")
                when(event) {
                    is DealsEvent.MainViewInit -> handleMainViewInit(event)
                    is DealsEvent.DealListViewInit -> handleDealsListViewInit(event)
                    is DealsEvent.DealListItemSelected -> handleDealsListItemSelected(event)
                    is DealsEvent.DealDetailsViewInit -> handleDealsDetailsViewInit(event)
                    is DealsEvent.DealDetailsViewInitWithId -> handleDealsDetailsViewInit(event)
                    is DealsEvent.PaymentViewClicked -> handlePaymentClicked(event)
                    is DealsEvent.CardNumberEntered -> handleCardNumberEntered(event)
                    is DealsEvent.PaymentCancelClicked -> handlePaymentCancelClicked(event)
                    is DealsEvent.PaymentSubmitClicked -> handlePaymentSubmitClicked(event)
                }
            }
            .launchIn(viewModelScope)

    }

    private fun updateState(state : InternalState) {
        _internalState.value = state
    }
    private fun sendNavigationEvent(singleNavEvent: SingeEvent<NavEvent>) {
        navigationEvent.value = singleNavEvent
    }

    fun handleEvent(event : DealsEvent) {
        channel.offer(event)
    }

    private fun handleMainViewInit(mainViewInit: DealsEvent.MainViewInit) {
        sendNavigationEvent(SingeEvent(NavEvent.FragmentNavEvent(DealListFragment::class.java, Bundle())))
    }

    private fun handleDealsListViewInit(event: DealsEvent.DealListViewInit) {
        viewModelScope.launch {
            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Show)))

            when(val result = getDealsUseCase.run()) {
                is Result.Success -> {
                    updateState(state.copy(
                            viewState = state.viewState.copy(
                                    processingViewState = ProcessingViewState.Hide,
                                    dealListViewState = DealListViewState.Show(result.value))))
                }
                is Result.Failure -> {
                    updateState(state.copy(
                            viewState = state.viewState.copy(
                                    errorViewState = ErrorViewState.Show(result.error.toString())
                            )
                    ))
                }
            }


        }
    }

    private fun handleDealsListItemSelected(listItemSelected: DealsEvent.DealListItemSelected) {
        sendNavigationEvent(SingeEvent(NavEvent.FragmentNavEvent(DealItemFragment::class.java, Bundle().apply {
            putParcelable(DealItemFragment.PRODUCT_EXTRA, listItemSelected.product)
        })))
    }

    private fun handleDealsDetailsViewInit(event: DealsEvent.DealDetailsViewInit) {
        event.bundle.getParcelable<Product>(DealItemFragment.PRODUCT_EXTRA)?.let { product ->
            updateState(state.copy(
                viewState = state.viewState.copy(
                    dealDetailsViewState = DealDetailsViewState.Show(product))))
        }
    }

    private fun handleDealsDetailsViewInit(event: DealsEvent.DealDetailsViewInitWithId) {
        viewModelScope.launch {
            updateState(state.copy(
                viewState = state.viewState.copy(
                    processingViewState = ProcessingViewState.Show)))

            when (val result = getDealsDetailsUseCase.run(event.id)) {
                is Result.Success -> {
                    updateState(state.copy(
                            viewState = state.viewState.copy(
                                    processingViewState = ProcessingViewState.Hide,
                                    dealDetailsViewState = DealDetailsViewState.Show(result.value))))
                }
                is Result.Failure -> {
                    updateState(state.copy(
                            viewState = state.viewState.copy(
                                    errorViewState = ErrorViewState.Show(result.error.toString())
                            )
                    ))
                }
            }
        }
    }

    private fun handlePaymentClicked(paymentViewClicked: DealsEvent.PaymentViewClicked) {
        updateState(state.copy(
            viewState = state.viewState.copy(
                dealsPaymentViewState = DealsPaymentViewState.Show)))
        sendNavigationEvent(SingeEvent(NavEvent.FragmentDialogNavEvent(PaymentDialogFragment::class.java, Bundle())))
    }

    private fun handleCardNumberEntered(cardNumberEntered: DealsEvent.CardNumberEntered) {
        when {
            cardValidator.isValidCard(cardNumberEntered.cardNumber) -> {
                updateState(state.copy(
                    viewState = state.viewState.copy(
                        dealsPaymentViewState = DealsPaymentViewState.EnableSubmit)))
            }
            else ->  {
                    updateState(state.copy(
                        viewState = state.viewState.copy(
                            dealsPaymentViewState = DealsPaymentViewState.DisableSubmit)))
            }
        }
    }

    private fun handlePaymentSubmitClicked(paymentSubmitClicked: DealsEvent.PaymentSubmitClicked) {
        updateState(state.copy(
            viewState = state.viewState.copy(
                dealsPaymentViewState = DealsPaymentViewState.Hide)))
    }

    private fun handlePaymentCancelClicked(paymentCancelClicked: DealsEvent.PaymentCancelClicked) {
        updateState(state.copy(
            viewState = state.viewState.copy(
                dealsPaymentViewState = DealsPaymentViewState.Hide)))
    }

    private fun getDefaultViewState() : ViewState {
        return ViewState(
            ProcessingViewState.Hide,
            DealListViewState.Hide,
            DealDetailsViewState.Hide,
            DealsPaymentViewState.Hide,
            ErrorViewState.Hide
        )
    }

    sealed class DealsEvent {
        object MainViewInit : DealsEvent()
        object DealListViewInit : DealsEvent()
        data class DealListItemSelected(val product: Product) : DealsEvent()
        data class DealDetailsViewInit(val bundle: Bundle) : DealsEvent()
        data class DealDetailsViewInitWithId(val id : Int) : DealsEvent()
        object PaymentViewClicked : DealsEvent()
        data class PaymentSubmitClicked(val cardNumber : String) : DealsEvent()
        data class CardNumberEntered(val cardNumber: String) : DealsEvent()
        object PaymentCancelClicked : DealsEvent()
    }

    data class InternalState(val viewState: ViewState)

    data class ViewState(
        val processingViewState: ProcessingViewState,
        val dealListViewState: DealListViewState,
        val dealDetailsViewState: DealDetailsViewState,
        val dealsPaymentViewState: DealsPaymentViewState,
        val errorViewState: ErrorViewState,
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

    sealed class DealsPaymentViewState {
        object Init : DealsPaymentViewState()
        object Hide : DealsPaymentViewState()
        object Show : DealsPaymentViewState()
        object EnableSubmit : DealsPaymentViewState()
        object DisableSubmit : DealsPaymentViewState()
    }

    sealed class ErrorViewState {
        object Hide : ErrorViewState()
        data class Show(val errorText : String) : ErrorViewState()
    }

    companion object {
        const val TAG = "DealsVM"
    }
}