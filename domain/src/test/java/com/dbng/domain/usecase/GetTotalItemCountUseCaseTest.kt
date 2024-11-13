package com.dbng.domain.usecase

import com.dbng.domain.repository.MenuRepository
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 13/11/24.

class GetTotalItemCountUseCaseTest{
    private lateinit var menuRepository: com.dbng.domain.repository.MenuRepository
    private lateinit var menuUseCase: com.dbng.domain.usecase.GetTotalItemCountUseCase
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        menuRepository = Mockito.mock(com.dbng.domain.repository.MenuRepository::class.java)
        menuUseCase = com.dbng.domain.usecase.GetTotalItemCountUseCase(menuRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test getTotalItemCount`() = runTest(testDispatcher) {
        val expectedCount = 10
        Mockito.`when`(menuRepository.getTotalItemCount()).thenReturn(expectedCount)
        val result = menuUseCase()
        assert(result == expectedCount)
    }
}