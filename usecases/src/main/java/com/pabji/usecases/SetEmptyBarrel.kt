package com.pabji.usecases

import com.pabji.data.repositories.BeerRepository

class SetEmptyBarrel(private val repository: BeerRepository) {
    suspend operator fun invoke(id: Long, isEmpty: Boolean) = repository.setEmptyBarrel(id, isEmpty)
}