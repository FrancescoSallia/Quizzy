package com.example.quizzy.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import com.example.quizzy.data.OpenTriviaAPI
import com.example.quizzy.data.local.RoomRepository
import com.example.quizzy.data.local.getDatabase
import com.example.quizzy.data.repository.getCategoryDrawable
import com.example.quizzy.model.Result
import com.example.quizzy.model.TriviaCategory
import com.example.quizzy.model.User
import kotlinx.coroutines.launch
import org.apache.commons.text.StringEscapeUtils


class MainViewModel(application: Application) : AndroidViewModel(application) {


    // region Room-Database
    private val database = getDatabase(application)
    private val roomRepository = RoomRepository(database)
    val userList = roomRepository.userList

    var currentUser = User(
        id = 0,
        rightAnswerList = emptyList(),
        wrongAnswerList = emptyList()
    )

    fun insertUser(user: User, context: Context) {
        viewModelScope.launch {
            roomRepository.insert(user, context)
        }
    }
    fun deleteById(userId: Int, context: Context) {
        viewModelScope.launch {
            roomRepository.deleteUser(userId, context)
        }
    }
    //endregion

    private var currentIndex = 0
    var rightAnswerClicked = 0
    var counterForAnimation = 1

    private val _randomQuizes = MutableLiveData<List<Result>?>()
    val randomQuizes: LiveData<List<Result>?>
        get() = _randomQuizes

    private val _categories = MutableLiveData<List<TriviaCategory>>()
    val categories: LiveData<List<TriviaCategory>>
        get() = _categories

    private val _getQuestions = MutableLiveData<List<Result>?>()
    val getQuestions: LiveData<List<Result>?>
        get() = _getQuestions

    private val _currentQuestion = MutableLiveData<Result?>(null)
    val currentQuestion: LiveData<Result?>
        get() = _currentQuestion

    private val _currentIndexProgressivBar = MutableLiveData<Int>()
    val currentIndexProgressivBar: LiveData<Int>
        get() = _currentIndexProgressivBar

        fun getRandomQuizes(context: Context) {
            viewModelScope.launch {
                try {
                    val quizFromApi = OpenTriviaAPI.retrofitService.getRandomQuizes()
                    _getQuestions.postValue(quizFromApi.results)
                    currentIndex = 0
                    _currentQuestion.postValue(quizFromApi.results[currentIndex])
                    _currentIndexProgressivBar.postValue(currentIndex)
                } catch (e: Exception) {
                    Log.e("error", "fun getRandomQuizes(viewModel): ${e.message}")
                    Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    fun getCategories(context: Context) {
            viewModelScope.launch {
                try {
                    val categoriesFromApi = OpenTriviaAPI.retrofitService.getCategories()
                    _categories.postValue(categoriesFromApi.triviaCategories)
                } catch (e: Exception) {
                    Log.e("error", "fun getCategories(viewModel): ${e.message}")
                    Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    //Diese Funktion ist dafür da, damit man das richtige Categorie item bekommt bei der aktuellen frage!
    fun getCategoryFromName(categoryName: String): Int {
        val findCategory = _categories.value?.find { category -> category.name == categoryName }
        Log.d("debug", "In MainViewModel: getCategoryFromName() return ID: ${findCategory?.id}")
        return getCategoryDrawable(findCategory!!.id)
    }
        fun getQuizQuestions(categorieInt: Int, context: Context) {
            viewModelScope.launch {
                try {
                    val questionsObjectApi =
                        OpenTriviaAPI.retrofitService.getQuizQuestions(categorieInt = categorieInt)
                    _getQuestions.postValue(questionsObjectApi.results)
                    currentIndex = 0
                    _currentQuestion.postValue(questionsObjectApi.results[currentIndex])
                } catch (e: Exception) {
                    Log.e("error", "fun getQuizQuestions(viewModel): ${e.message}")
                    Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
        fun showNextQuestion(navigateToCompletedFragment: () -> Unit) {
            _getQuestions.value?.let { questions ->
                if (currentIndex + 1 < questions.size) {
                    currentIndex++
                    _currentQuestion.postValue(questions[currentIndex])
                    _currentIndexProgressivBar.postValue(currentIndex)
                } else {
                    counterForAnimation = 1
                    navigateToCompletedFragment() // Navigiert weiter wenn alle fragen durch sind
                }
            }
        }
        fun resetGetQuestions() {
            _getQuestions.value = null
            _currentQuestion.value = null
            _randomQuizes.value = null
            _currentIndexProgressivBar.value = 0
        }

    fun decodeText(text: String): String {
        return StringEscapeUtils.unescapeHtml4(text)
    }

}

