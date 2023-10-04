package com.example.quizgamemvvm.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentSignInBinding
import com.example.quizgamemvvm.viewmodel.AuthViewModel
import com.example.quizgamemvvm.viewmodel.IAuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment() {

    private lateinit var fragmentSignInBinding: FragmentSignInBinding
    private lateinit var authViewModel: IAuthViewModel
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        authViewModel.getUserData().observe(this,
            Observer<FirebaseUser?> { firebaseUser ->
                if (firebaseUser != null) {
                    navController.navigate(R.id.action_signInFragment_to_homeFragment)
                }
            }
        )

        auth = Firebase.auth
        val googleSignInOptions  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("689076638005-j1jmdunonnlgb4brrvu4ro9ct8qq0rmi.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return fragmentSignInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInGgBtn = fragmentSignInBinding.btnSignInGg.getChildAt(0) as TextView
        signInGgBtn.text = getString(R.string.login_google)
        signInGgBtn.setTextColor(Color.BLACK)
        signInGgBtn.textSize = 18F

        navController = Navigation.findNavController(view)

        fragmentSignInBinding.tvSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        fragmentSignInBinding.tvForgotPassword.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        fragmentSignInBinding.btnSignIn.setOnClickListener {
            val email = fragmentSignInBinding.editTextLoginEmail.text.toString()
            val password = fragmentSignInBinding.editTextLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signIn(email, password)
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        fragmentSignInBinding.btnSignInGg.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        // Initialize sign in intent
        val intent = googleSignInClient.signInIntent
        // Start activity for result
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            // check condition
            if (signInAccountTask.isSuccessful) {
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                        // Check credential
                        authViewModel.signInWithGoogle(authCredential)
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            } else {
                Log.d("error task sign in google",
                    signInAccountTask.exception.toString()
                )
            }
        } else {
            Toast.makeText(requireActivity(), "error sign in google", Toast.LENGTH_SHORT).show()
        }
    }
}