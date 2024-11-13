package com.dbng.domain.usecase

import com.dbng.domain.repository.MenuRepository
import javax.inject.Inject

class  GetTotalItemCountUseCase @Inject constructor(
    private val repository : MenuRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getTotalItemCount()
    }
}