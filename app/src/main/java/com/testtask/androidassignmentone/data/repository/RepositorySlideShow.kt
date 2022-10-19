package com.testtask.androidassignmentone.data.repository

import com.testtask.androidassignmentone.data.network.ApiManager
import com.testtask.androidassignmentone.data.network.model.SlideshowModel

class RepositorySlideShow(private val api: ApiManager) {

    suspend fun getSlideshow(): List<SlideshowModel>? {
        val response = api.restApiNoHeader().getSlideshow()
        return if (response.isSuccessful) {
            response.body()
        } else {
            listOf()
        }
    }
}