package com.pabji.usecases

import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow

class GetBeerDetail(private val repository: BeerRepository) {
    suspend operator fun invoke(id: Long): Flow<Beer> = repository.getBeerDetail(id)
}