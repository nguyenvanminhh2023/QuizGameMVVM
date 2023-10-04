package com.example.quizgamemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthViewModel {
    fun signUp(email: String, pass: String)
    fun signIn(email: String, pass: String)
    fun signOut()
    fun forgotPassword(email: String)
    fun signInWithGoogle(googleAuthCredential: AuthCredential)
    fun getUserData(): MutableLiveData<FirebaseUser>
}