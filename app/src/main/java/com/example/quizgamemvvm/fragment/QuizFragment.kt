package com.example.quizgamemvvm.fragment

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgamemvvm.R
import com.example.quizgamemvvm.databinding.FragmentQuizBinding
import com.example.quizgamemvvm.model.QuestionModel
import com.example.quizgamemvvm.viewmodel.QuestionViewModel

class QuizFragment : Fragment() {

    private lateinit var fragmentQuizBinding: FragmentQuizBinding
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var navController: NavController

    private var listQuestions = ArrayList<QuestionModel>()
    private lateinit var countDown: CountDownTimer
    private var questionTotal = 0
    private var questionIndex = 0
    private var correctCount = 0
    private var wrongCount = 0
    private var correctAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[QuestionViewModel::class.java]
        questionViewModel.getQuestions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentQuizBinding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return fragmentQuizBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        questionViewModel.questionMutableLiveData.observe(viewLifecycleOwner) {
            listQuestions.addAll(it)
            questionTotal = listQuestions.size
            showQuestions()
        }

        fragmentQuizBinding.tvA.setOnClickListener {
            if (correctAnswer == "a") {
                fragmentQuizBinding.tvA.setBackgroundColor(Color.GREEN)
                correctCount++
                fragmentQuizBinding.tvCorrect.text = correctCount.toString()
            } else {
                fragmentQuizBinding.tvA.setBackgroundColor(Color.RED)
                wrongCount++
                fragmentQuizBinding.tvWrong.text = wrongCount.toString()
                showCorrectAnswer()
            }
            stopCountDown()
            disableChooseAnswers()
        }

        fragmentQuizBinding.tvB.setOnClickListener {
            if (correctAnswer == "b") {
                fragmentQuizBinding.tvB.setBackgroundColor(Color.GREEN)
                correctCount++
                fragmentQuizBinding.tvCorrect.text = correctCount.toString()
            } else {
                fragmentQuizBinding.tvB.setBackgroundColor(Color.RED)
                wrongCount++
                fragmentQuizBinding.tvWrong.text = wrongCount.toString()
                showCorrectAnswer()
            }
            stopCountDown()
            disableChooseAnswers()
        }

        fragmentQuizBinding.tvC.setOnClickListener {
            if (correctAnswer == "c") {
                fragmentQuizBinding.tvC.setBackgroundColor(Color.GREEN)
                correctCount++
                fragmentQuizBinding.tvCorrect.text = correctCount.toString()
            } else {
                fragmentQuizBinding.tvC.setBackgroundColor(Color.RED)
                wrongCount++
                fragmentQuizBinding.tvWrong.text = wrongCount.toString()
                showCorrectAnswer()
            }
            stopCountDown()
            disableChooseAnswers()
        }

        fragmentQuizBinding.tvD.setOnClickListener {
            if (correctAnswer == "d") {
                fragmentQuizBinding.tvD.setBackgroundColor(Color.GREEN)
                correctCount++
                fragmentQuizBinding.tvCorrect.text = correctCount.toString()
            } else {
                fragmentQuizBinding.tvD.setBackgroundColor(Color.RED)
                wrongCount++
                fragmentQuizBinding.tvWrong.text = wrongCount.toString()
                showCorrectAnswer()
            }
            stopCountDown()
            disableChooseAnswers()
        }

        fragmentQuizBinding.btnNext.setOnClickListener {
            showQuestions()
        }

        fragmentQuizBinding.btnFinish.setOnClickListener {

        }
    }

    private fun showQuestions() {
        if (questionIndex <= questionTotal - 1) {
            resetQuestionUI()

            val question = listQuestions[questionIndex]
            fragmentQuizBinding.tvQuestion.text = question.question
            fragmentQuizBinding.tvA.text = question.optionA
            fragmentQuizBinding.tvB.text = question.optionB
            fragmentQuizBinding.tvC.text = question.optionC
            fragmentQuizBinding.tvD.text = question.optionD
            correctAnswer = question.correctAnswer

            fragmentQuizBinding.progressBarQuestion.visibility = View.INVISIBLE
            fragmentQuizBinding.linearLayoutQuestion.visibility = View.VISIBLE
            fragmentQuizBinding.linearLayoutBtn.visibility = View.VISIBLE
            fragmentQuizBinding.linearLayoutStatus.visibility = View.VISIBLE
        } else {
            showDialog()
        }
        questionIndex++
    }

    private fun resetQuestionUI() {
        startCountDown()
        fragmentQuizBinding.tvA.setBackgroundColor(Color.WHITE)
        fragmentQuizBinding.tvB.setBackgroundColor(Color.WHITE)
        fragmentQuizBinding.tvC.setBackgroundColor(Color.WHITE)
        fragmentQuizBinding.tvD.setBackgroundColor(Color.WHITE)

        fragmentQuizBinding.tvA.isClickable = true
        fragmentQuizBinding.tvB.isClickable = true
        fragmentQuizBinding.tvC.isClickable = true
        fragmentQuizBinding.tvD.isClickable = true
    }

    private fun startCountDown() {
        countDown = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                fragmentQuizBinding.tvTime.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                disableChooseAnswers()
                fragmentQuizBinding.tvQuestion.text = getString(R.string.times_up)
            }
        }.start()
    }

    private fun disableChooseAnswers() {
        fragmentQuizBinding.tvA.isClickable = false
        fragmentQuizBinding.tvB.isClickable = false
        fragmentQuizBinding.tvC.isClickable = false
        fragmentQuizBinding.tvD.isClickable = false
    }

    private fun showCorrectAnswer() {
        when (correctAnswer) {
            "a" -> fragmentQuizBinding.tvA.setBackgroundColor(Color.GREEN)
            "b" -> fragmentQuizBinding.tvB.setBackgroundColor(Color.GREEN)
            "c" -> fragmentQuizBinding.tvC.setBackgroundColor(Color.GREEN)
            "d" -> fragmentQuizBinding.tvD.setBackgroundColor(Color.GREEN)
        }
    }

    private fun stopCountDown() {
        countDown.cancel()
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Quiz Game")
            .setMessage(R.string.dialog_message)
            .setPositiveButton("SEE RESULT") { _: DialogInterface, _ ->
                val action = QuizFragmentDirections.actionQuizragmentToResultFragment(
                    correctArg = correctCount,
                    wrongArg = wrongCount
                )
                navController.navigate(action)
            }
            .setNegativeButton("PLAY AGAIN") { _: DialogInterface, _ ->
                navController.popBackStack()
            }
            .create()
            .show()
    }
}