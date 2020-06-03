package com.example.lab_8.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_8.network.HackerNewsApi
import com.example.lab_8.network.HackerNewsApiStatus
import com.example.lab_8.network.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<HackerNewsApiStatus>()
    val status: LiveData<HackerNewsApiStatus>
    get() = _status

    private val _news = MutableLiveData<String>()
    val news: LiveData<String>
    get() = _news

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init{
        startStatus()
    }

    fun startStatus(){
        _status.value = HackerNewsApiStatus.START
    }
    fun searchNews(keyWord:String, tag:String,points:String,author:String) {
        coroutineScope.launch {
            val authors = "author_"+author
            val  getPropertiesDeferred = HackerNewsApi.retrofitService.getProperties(keyWord,tag)
            try{
                _status.value = HackerNewsApiStatus.LOADING
                var listResult = getPropertiesDeferred.await()
                _status.value = HackerNewsApiStatus.DONE
                _news.value = listResult.urlNews
            }catch (e: Exception){
                _status.value = HackerNewsApiStatus.ERROR
                throw e
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
