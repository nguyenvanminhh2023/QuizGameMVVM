package com.example.quizgamemvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(application)
    val userData: MutableLiveData<FirebaseUser> = repository.firebaseUserMutableLiveData
    val loggedStatus = repository.userLoggedMutableLiveData

    fun signUp(email: String?, pass: String?) {
        repository.signUp(email, pass)
    }

    fun signIn(email: String?, pass: String?) {
        repository.signIn(email, pass)
    }

    fun signOut() {
        repository.signOut()
    }

    fun forgotPassword(email: String?) {
        repository.forgotPassword(email)
    }
}