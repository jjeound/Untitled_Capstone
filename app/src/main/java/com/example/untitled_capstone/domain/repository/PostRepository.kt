package com.example.untitled_capstone.domain.repository

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.example.untitled_capstone.core.util.Resource
import com.example.untitled_capstone.data.local.entity.PostItemEntity
import com.example.untitled_capstone.data.util.PostFetchType
import com.example.untitled_capstone.domain.model.Keyword
import com.example.untitled_capstone.domain.model.NewPost
import com.example.untitled_capstone.domain.model.Post
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepository {
    @WorkerThread
    fun post(newPost: RequestBody, images: List<MultipartBody.Part>?): Flow<Resource<String>>
    @WorkerThread
    fun getMyPosts(fetchType: PostFetchType): Flow<PagingData<PostItemEntity>>
    @WorkerThread
    fun getPostById(id: Long): Flow<Resource<Post>>
    @WorkerThread
    fun deletePost(id: Long): Flow<Resource<String>>
    @WorkerThread
    fun modifyPost(id: Long, newPost: NewPost): Flow<Resource<String>>
    @WorkerThread
    fun searchPosts(fetchType: PostFetchType): Flow<PagingData<PostItemEntity>>
    @WorkerThread
    fun toggleLike(id: Long): Flow<Resource<Boolean>>
    @WorkerThread
    fun getLikedPosts(fetchType: PostFetchType): Flow<PagingData<PostItemEntity>>
    @WorkerThread
    fun uploadImages(id: Long, images: List<MultipartBody.Part>): Flow<Resource<String>>
    @WorkerThread
    fun deleteImage(id: Long, imageId: Long): Flow<Resource<String>>
    @WorkerThread
    fun getSearchHistory(): Flow<Resource<List<Keyword>>>
    @WorkerThread
    fun deleteSearchHistory(keyword: String): Flow<Resource<String>>
    @WorkerThread
    fun deleteAllSearchHistory(): Flow<Resource<String>>
    @WorkerThread
    fun reportPost(postId: Long, reportType: String, content: String): Flow<Resource<String>>
}