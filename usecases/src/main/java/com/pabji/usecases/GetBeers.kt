package com.pabji.usecases

import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow

class GetBeers(
    private val repository: BeerRepository,
    private val limit: Int,
    private val max: Int
) {
    suspend operator fun invoke(): Flow<List<Beer>> = repository.getBeers(limit, max)
}