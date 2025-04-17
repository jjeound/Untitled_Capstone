package com.example.untitled_capstone.domain.use_case.post

import com.example.untitled_capstone.core.util.Resource
import com.example.untitled_capstone.domain.repository.PostRepository
import javax.inject.Inject

class ReportPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: Long, reportType: String, content: String): Resource<String> {
        return repository.reportPost(postId, reportType, content)
    }
}