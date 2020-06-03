package com.example.lab_8.news

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
import javax.net.ssl.HandshakeCompletedEvent

class NewsViewModel () : ViewModel() {

    private val _hackerNews = MutableLiveData<List<News>>()
    val hackerNews: LiveData<List<News>>
        get() = _hackerNews

    private val _status = MutableLiveData<HackerNewsApiStatus>()
    val status: LiveData<HackerNewsApiStatus>
        get() = _status

    private val _currentHackerNew = MutableLiveData<News>()
    val currentHackerNew: LiveData<News>
        get() = _currentHackerNew

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        startStatus()
        getNews()
    }

    fun startStatus(){
        _status.value = HackerNewsApiStatus.START
    }

    fun openRepoUrl(hackerNews2: News){
        _currentHackerNew.value = hackerNews2
    }

    private fun getNews(keyWord:String,points:String, author: String){
        coroutineScope.launch {
            val newsDeferred = HackerNewsApi.retrofitService.getProperties(keyWord,points)
            try {
                _status.value = NewsApiStatus.LOADING
                val news = newsDeferred.await()
                _status.value = NewsApiStatus.DONE
                _hackerNews.value = news
            } catch (e: Exception){
                _status.value = NewsApiStatus.ERROR
                _hackerNews.value = emptyList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
