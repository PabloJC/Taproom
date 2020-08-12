package com.pabji.usecases

import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow

class GetBeers(private val repository: BeerRepository) {

    suspend operator fun invoke(): Flow<List<ItemBeer>> = repository.getBeers()
}