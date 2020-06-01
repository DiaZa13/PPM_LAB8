package com.example.lab_8.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_8.network.HackerNewsApi
import com.example.lab_8.network.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _prueba = MutableLiveData<String>()
    val prueba: LiveData<String>
    get() = _prueba

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getHackersNewsProperties()
    }

    private fun getHackersNewsProperties() {
        coroutineScope.launch {

            var getPropertiesDeferred = HackerNewsApi.retrofitService.getProperties()
            try{
                var listResult = getPropertiesDeferred.await()
                _prueba.value = "Succes: ${listResult?.size} stories found"
            }catch (t: Throwable){
                _prueba.value = "Failrue: " + t.message
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
