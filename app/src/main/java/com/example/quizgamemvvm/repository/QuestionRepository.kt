package com.example.quizgamemvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.model.QuestionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionRepository {
    private val database = Firebase.database
    private val myRef = database.reference.child("questions")

    fun getQuestions(liveData: MutableLiveData<List<QuestionModel>>) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("data", snapshot.value.toString())
                val questions: List<QuestionModel> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(QuestionModel::class.java)!!.copy(
                        questionNumber = dataSnapshot.key,
                        question = dataSnapshot.child("q").value.toString(),
                        optionA = dataSnapshot.child("a").value.toString(),
                        optionB = dataSnapshot.child("b").value.toString(),
                        optionC = dataSnapshot.child("c").value.toString(),
                        optionD = dataSnapshot.child("d").value.toString(),
                        correctAnswer = dataSnapshot.child("answer").value.toString()
                    )
                }
                liveData.postValue(questions)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}