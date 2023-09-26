package com.example.quizgamemvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentSignUpBinding
import com.example.quizgamemvvm.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

class SignUpFragment : Fragment() {

    private lateinit var fragmentSignUpBinding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(
            AuthViewModel::class.java
        )

        authViewModel.userData.observe(this,
            Observer<FirebaseUser?> { firebaseUser ->
                if (firebaseUser != null) {
                    navController.popBackStack()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return fragmentSignUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        fragmentSignUpBinding.btnSignUp.setOnClickListener {
            val email = fragmentSignUpBinding.editTextSignUpEmail.text.toString()
            val password = fragmentSignUpBinding.editTextSignUpPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signUp(email, password)
//                authViewModel.userData
//                    .observe(viewLifecycleOwner) { firebaseUser ->
//                        if (firebaseUser != null) {
//                            navController.popBackStack()
//                        }
//                    }
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}