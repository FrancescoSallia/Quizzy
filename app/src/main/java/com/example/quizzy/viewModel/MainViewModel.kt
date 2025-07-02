package com.example.quizzy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzy.data.OpenTriviaAPI
import com.example.quizzy.model.Categorie
import com.example.quizzy.model.Result
import com.example.quizzy.model.TriviaCategory
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    private val _randomQuizes = MutableLiveData<List<Result>>()
    val randomQuizes: LiveData<List<Result>>
    get() = _randomQuizes

    private val _categories = MutableLiveData<List<TriviaCategory>>()
    val categories: LiveData<List<TriviaCategory>>
    get() = _categories


    fun getRandomQuizes(){
        viewModelScope.launch {
            try {
                val quizFromApi = OpenTriviaAPI.retrofitService.getRandomQuizes()
                _randomQuizes.postValue(quizFromApi.results)
            } catch (e: Exception) {
                Log.e("error", "fun getRandomQuizes(viewModel): ${e.message}")
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
                val categoriesFromApi = OpenTriviaAPI.retrofitService.getCategories()
                _categories.postValue(categoriesFromApi.triviaCategories)
            } catch (e: Exception) {
                Log.e("error", "fun getCategories(viewModel): ${e.message}")
            }
        }
    }



}

