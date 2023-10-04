package com.example.quizgamemvvm.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizgamemvvm.repository.AuthRepository
import com.example.quizgamemvvm.repository.IAuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application), IAuthViewModel {
    private val repository: IAuthRepository = AuthRepository(application)
    private val userData: MutableLiveData<FirebaseUser> = repository.getFirebaseUserMutableLiveData()
//    val loggedStatus = repository.userLoggedMutableLiveData

    override fun signUp(email: String, pass: String) {
        repository.signUp(email, pass)
    }

    override fun signIn(email: String, pass: String) {
        repository.signIn(email, pass)
    }

    override fun signOut() {
        repository.signOut()
    }

    override fun forgotPassword(email: String) {
        repository.forgotPassword(email)
    }

    override fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        repository.firebaseSignInWithGoogle(googleAuthCredential)
    }

    override fun getUserData(): MutableLiveData<FirebaseUser> = userData
}