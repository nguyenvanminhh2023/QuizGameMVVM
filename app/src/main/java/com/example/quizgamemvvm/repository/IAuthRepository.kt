package com.example.quizgamemvvm.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {
    fun signUp(email: String, pass: String)
    fun signIn(email: String, pass: String)
    fun signOut()
    fun forgotPassword(email: String)
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential)
    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser>
}