package com.pabji.usecases

import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow

class GetBeers(private val repository: BeerRepository) {
    suspend operator fun invoke(): Flow<List<Beer>> = repository.getBeers()
}