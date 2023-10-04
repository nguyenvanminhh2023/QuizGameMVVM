package com.example.quizgamemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.model.QuestionModel

interface IQuestionViewModel {
    fun getQuestions()
    fun sendScore(questionTotal: Int, correctCount: Int)
    fun getQuestionLiveData(): MutableLiveData<List<QuestionModel>>
}