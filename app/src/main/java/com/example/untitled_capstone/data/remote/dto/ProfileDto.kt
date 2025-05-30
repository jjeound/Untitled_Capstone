package com.example.untitled_capstone.data.remote.dto

import com.example.untitled_capstone.domain.model.Profile

data class ProfileDto(
    val id: Long,
    val email: String,
    val nickname: String?,
    val imageUrl: String?,
    val trustLevelImageUrl: String,
    val trustLevel: String
) {
    fun toProfile(): Profile{
        return Profile(
            id = id,
            email = email,
            nickname = nickname,
            imageUrl = imageUrl,
            trustLevelImageUrl = trustLevelImageUrl,
            trustLevel = trustLevel
        )
    }
}