package com.example.quizgamemvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
    val currentUser: FirebaseUser?
    private val repository: AuthRepository

    init {
        repository = AuthRepository(application)
        currentUser = repository.currentUser
        firebaseUserMutableLiveData = repository.firebaseUserMutableLiveData
    }

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