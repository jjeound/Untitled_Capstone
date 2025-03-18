package com.example.untitled_capstone.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.untitled_capstone.core.util.Resource
import com.example.untitled_capstone.data.local.db.FridgeItemDatabase
import com.example.untitled_capstone.data.local.entity.FridgeItemEntity
import com.example.untitled_capstone.data.pagination.FridgePagingSource
import com.example.untitled_capstone.data.remote.service.Api
import com.example.untitled_capstone.data.remote.dto.ContentDto
import com.example.untitled_capstone.data.remote.dto.Response
import com.example.untitled_capstone.domain.repository.FridgeRepository
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException


class FridgeRepositoryImpl(
    private val api: Api,
    private val db: FridgeItemDatabase,
): FridgeRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFridgeItemsPaged(): Flow<PagingData<FridgeItemEntity>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = FridgePagingSource(api, db),
            pagingSourceFactory = { db.dao.getFridgeItems() }
        ).flow
    }


    override suspend fun addItem(item: ContentDto): Resource<Response> {
        return try {
            val response = api.addFridgeItem(item)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(e.toString())
        } catch (e: HttpException) {
            Resource.Error(e.toString())
        }
    }

    override suspend fun toggleNotification(id: Int): Resource<Response> {
        return try {
            val response = api.toggleNotification(id)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(e.toString())
        } catch (e: HttpException) {
            Resource.Error(e.toString())
        }
    }

    override suspend fun modifyItem(updatedItem: ContentDto): Resource<Response> {
        return try {
            val response = api.modifyItem(updatedItem.id, updatedItem)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(e.toString())
        } catch (e: HttpException) {
            Resource.Error(e.toString())
        }
    }

    override suspend fun deleteItem(id: Int): Resource<Response> {
        return try {
            val response = api.deleteItem(id)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(e.toString())
        } catch (e: HttpException) {
            Resource.Error(e.toString())
        }
    }

    override fun invalidatePagingSource() {
        db.dao.getFridgeItems().invalidate()
    }

//    override suspend fun getFridgeItemById(id: Int): Resource<ContentDto?> {
//        return try {
//            val response = api.getFridgeItemById(id)
//            Resource.Success(response.result)
//        } catch (e: IOException) {
//            Resource.Error(e.toString())
//        } catch (e: HttpException) {
//            Resource.Error(e.toString())
//        }
//    }


    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}