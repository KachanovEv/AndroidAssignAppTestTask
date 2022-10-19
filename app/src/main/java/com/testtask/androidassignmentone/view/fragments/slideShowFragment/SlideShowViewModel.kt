package com.testtask.androidassignmentone.view.fragments.slideShowFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.androidassignmentone.data.network.model.SlideshowModel
import com.testtask.androidassignmentone.data.repository.RepositorySlideShow
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideShowViewModel(private val repository: RepositorySlideShow) : ViewModel() {

    val slides: MutableLiveData<List<SlideshowModel>?> = MutableLiveData()

    fun getSLideShowData() {
        viewModelScope.launch(IO) {
            val result = repository.getSlideshow()
            withContext(Main) {
                if (result?.isNotEmpty() == true) {
                    slides.postValue(result)
                } else {
                    slides.postValue(listOf())
                }
            }
        }
    }
}

