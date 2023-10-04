package com.example.quizgamemvvm.repository

import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.model.QuestionModel

interface IQuestionRepository {
    fun getQuestions(liveData: MutableLiveData<List<QuestionModel>>)
    fun sendScore(questionTotal: Int, correctCount: Int)
}