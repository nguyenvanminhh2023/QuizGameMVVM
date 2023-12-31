package com.example.quizgamemvvm.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val application: Application) : IAuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
//    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun signUp(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                Toast.makeText(application, "Sign up successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun signIn(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                Log.d("current user sign in", firebaseAuth.currentUser.toString())
                Toast.makeText(application, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
        firebaseUserMutableLiveData.postValue(null)
//        userLoggedMutableLiveData.postValue(true)
        Toast.makeText(application, "Sign out successfully", Toast.LENGTH_SHORT).show()
    }

    override fun forgotPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(application, "Password reset has been sent to your email address", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential) {
        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                Toast.makeText(application, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser> = firebaseUserMutableLiveData
}