package com.example.quizgamemvvm.model

data class QuestionModel(
    var questionNumber: String? = "",
    var question: String = "",
    var optionA: String = "",
    var optionB: String = "",
    var optionC: String = "",
    var optionD: String = "",
    var correctAnswer: String = ""
)