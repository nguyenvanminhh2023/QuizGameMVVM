package com.example.quizgamemvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.model.QuestionModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionRepository : IQuestionRepository {
    private val database = Firebase.database
    private val myRef = database.reference.child("questions")
    private val scoreRef = database.reference

    override fun getQuestions(liveData: MutableLiveData<List<QuestionModel>>) {
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

    override fun sendScore(questionTotal: Int, correctCount: Int) {
        Firebase.auth.currentUser?.let {
            val userUID = Firebase.auth.currentUser?.uid.toString()
            scoreRef.child("scores").child(userUID).child("correct").setValue(correctCount)
            scoreRef.child("scores").child(userUID).child("wrong").setValue(questionTotal - correctCount)
        }
    }
}