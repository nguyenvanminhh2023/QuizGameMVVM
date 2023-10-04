package com.example.quizgamemvvm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentHomeBinding
import com.example.quizgamemvvm.viewmodel.AuthViewModel
import com.example.quizgamemvvm.viewmodel.IAuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

class HomeFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var authViewModel: IAuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

//        authViewModel.loggedStatus.observe(this,
//            Observer<Boolean> { status ->
//                if (status) {
//                    navController.popBackStack()
//                }
//            }
//        )

        authViewModel.getUserData().observe(this,
            Observer<FirebaseUser?> { firebaseUser ->
                if (firebaseUser == null) {
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
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        fragmentHomeBinding.btnStartQuiz.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_quizFragment)
        }

        fragmentHomeBinding.btnSignOut.setOnClickListener {
            authViewModel.signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build()
            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut()
        }
    }
}