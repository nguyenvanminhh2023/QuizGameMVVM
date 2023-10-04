package com.example.quizgamemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgamemvvm.model.QuestionModel
import com.example.quizgamemvvm.repository.IQuestionRepository
import com.example.quizgamemvvm.repository.QuestionRepository

class QuestionViewModel : ViewModel(), IQuestionViewModel {
    private val questionMutableLiveData = MutableLiveData<List<QuestionModel>>()
    private var questionRepository: IQuestionRepository = QuestionRepository()

    override fun getQuestions() {
        questionRepository.getQuestions(questionMutableLiveData)
    }

    override fun sendScore(questionTotal: Int, correctCount: Int) {
        questionRepository.sendScore(questionTotal, correctCount)
    }

    override fun getQuestionLiveData() = questionMutableLiveData
}