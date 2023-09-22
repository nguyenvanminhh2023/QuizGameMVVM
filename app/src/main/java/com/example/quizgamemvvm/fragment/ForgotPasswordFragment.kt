package com.example.quizgamemvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentForgotPasswordBinding
import com.example.quizgamemvvm.viewmodel.AuthViewModel

class ForgotPasswordFragment : Fragment() {

    private lateinit var fragmentForgotPasswordBinding: FragmentForgotPasswordBinding
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentForgotPasswordBinding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return fragmentForgotPasswordBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        fragmentForgotPasswordBinding.btnReset.setOnClickListener {
            val email = fragmentForgotPasswordBinding.editTextForgotEmail.text.toString()
            if (email.isNotEmpty()) {
                authViewModel.forgotPassword(email)
                navController.popBackStack()
            } else {
                Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}