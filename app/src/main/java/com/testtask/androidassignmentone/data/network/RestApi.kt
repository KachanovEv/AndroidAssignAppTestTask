package com.testtask.androidassignmentone.data.network

import com.testtask.androidassignmentone.data.network.model.SlideshowModel
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {
    @GET("/ShkurtiA/de0d1cd3499cca811566868b70c1dfb0/raw/e710f4efe6ab10db0c9d7c10e0dfa1f8bb2f3a7b/slideshow.json")
    suspend fun getSlideshow(): Response<List<SlideshowModel>>
}