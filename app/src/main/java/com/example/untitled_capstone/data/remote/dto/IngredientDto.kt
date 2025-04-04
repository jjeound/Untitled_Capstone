package com.example.untitled_capstone.data.remote.dto

import com.example.untitled_capstone.domain.model.Ingredient

data class IngredientDto(
    val alarmStatus: String,
    val count: Int,
    val createdAt: String,
    val foodName: String,
    val id: Int,
    val storageType: String,
    val updatedAt: String,
    val useByDate: String,
    val user: String
){
    fun toIngredient(): Ingredient{
        return Ingredient(
            alarmStatus = alarmStatus,
            count = count,
            createdAt = createdAt,
            foodName = foodName,
            id = id,
            storageType = storageType,
            updatedAt = updatedAt,
            useByDate = useByDate,
            user = user
        )
    }
}