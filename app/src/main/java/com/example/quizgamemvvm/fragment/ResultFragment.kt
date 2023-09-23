package com.example.quizgamemvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var fragmentResultBinding: FragmentResultBinding
    val args: ResultFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentResultBinding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return fragmentResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val correct = args.correctArg
        val wrong = args.wrongArg

        fragmentResultBinding.tvCorrectResult.text = correct.toString()
        fragmentResultBinding.tvWrongResult.text = wrong.toString()

        fragmentResultBinding.btnPlayAgain.setOnClickListener {
            navController.popBackStack(R.id.homeFragment, false)
        }

        fragmentResultBinding.btnExit.setOnClickListener {
            navController.popBackStack(R.id.homeFragment, false)
        }
    }
}