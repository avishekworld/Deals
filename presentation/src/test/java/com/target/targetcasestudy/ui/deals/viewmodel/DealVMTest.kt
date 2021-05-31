package com.target.targetcasestudy.ui.deals.viewmodel

import android.content.Context
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Result
import life.avishekworld.domain.usecase.deals.GetDealsDetailsUseCase
import life.avishekworld.domain.usecase.deals.GetDealsUseCase
import life.avishekworld.domain.util.CardValidator
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class DealVMTest {
    private val getDealsUseCase = mockk<GetDealsUseCase>()
    private val getDealsDetailsUseCase = mockk<GetDealsDetailsUseCase>()
    private val cardValidator = mockk<CardValidator>()
    private val context = mockk<Context>()

    private val dealsVM = DealVM(getDealsUseCase, getDealsDetailsUseCase, cardValidator, context)

    @Test
    fun testHandleEventDealsListViewInit() = runBlocking {
        val deals = mockk<Deals>()
        val result = Result.Success(deals)
        val viewStateList = mutableListOf<DealVM.ViewState>()
        val processingViewEventId = 1
        val dealsListViewEventId = 2

        val observer = Observer { viewState : DealVM.ViewState -> viewStateList.add(viewState) }
        dealsVM.viewState.observeForever(observer)
        val event = DealVM.DealsEvent.DealListViewInit

        with(getDealsUseCase) {
            coEvery {
                run()
            } returns result
        }

        dealsVM.handleEvent(event)
        assertTrue(viewStateList[processingViewEventId].processingViewState is DealVM.ProcessingViewState.Show)
        assertTrue(viewStateList[dealsListViewEventId].processingViewState is DealVM.ProcessingViewState.Hide)
        assertTrue(viewStateList[dealsListViewEventId].dealListViewState is DealVM.DealListViewState.Show)
        assertEquals(deals, (viewStateList[dealsListViewEventId].dealListViewState as DealVM.DealListViewState.Show).deals)
    }
}