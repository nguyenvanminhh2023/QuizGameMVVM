package com.example.quizgamemvvm.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentSignInBinding
import com.example.quizgamemvvm.viewmodel.AuthViewModel

class SignInFragment : Fragment() {

    private lateinit var fragmentSignInBinding: FragmentSignInBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[AuthViewModel::class.java]
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
                authViewModel.firebaseUserMutableLiveData
                    .observe(viewLifecycleOwner) { firebaseUser ->
                        if (firebaseUser != null) {
                            navController.navigate(R.id.action_signInFragment_to_homeFragment)
                        }
                    }
//                Toast.makeText(context, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}