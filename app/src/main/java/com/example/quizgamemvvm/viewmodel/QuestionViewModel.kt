package com.example.quizgamemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgamemvvm.model.QuestionModel
import com.example.quizgamemvvm.repository.QuestionRepository

class QuestionViewModel : ViewModel() {
    val questionMutableLiveData = MutableLiveData<List<QuestionModel>>()
    private val questionRepository = QuestionRepository()

    fun getQuestions() {
        questionRepository.getQuestions(questionMutableLiveData)
    }
}