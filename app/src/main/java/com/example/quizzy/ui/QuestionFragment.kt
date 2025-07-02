package com.example.quizzy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentQuestionBinding
import com.example.quizzy.viewModel.MainViewModel

class QuestionFragment : Fragment() {
    private lateinit var vb: FragmentQuestionBinding
    private val args: QuestionFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentQuestionBinding.inflate(inflater,container,false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //back button vom handy kann jetzt nicht wieder zurück navigieren!
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            val answerList: MutableList<String> = currentQuestion.incorrectAnswers.toMutableList()
            answerList.add(currentQuestion.correctAnswer)
            val shuffledAnswers = answerList.shuffled()

            vb.tvQuestion.text = currentQuestion.question

            val answerViews = listOf(
                vb.tvAnswerOne,
                vb.tvAnswerTwo,
                vb.tvAnswerThree,
                vb.tvAnswerFour
            )

            val cardViews = listOf(
                vb.cvAnswerOneVisibility,
                vb.cvAnswerTwoVisibility,
                vb.cvAnswerThreeVisibility,
                vb.cvAnswerFourVisibility
            )

            // Antworten zurücksetzen
            for (i in answerViews.indices) {
                if (i < shuffledAnswers.size) {
                    answerViews[i].visibility = View.VISIBLE
                    answerViews[i].text = shuffledAnswers[i]
                    cardViews[i].foreground = null
                    cardViews[i].isEnabled = true
                    cardViews[i].visibility = View.VISIBLE
                } else {
                    answerViews[i].visibility = View.GONE
                    cardViews[i].visibility = View.GONE
                }
            }

            vb.btnContinue.isEnabled = false
            vb.btnContinue.visibility = View.GONE


            for (i in cardViews.indices) {
                cardViews[i].setOnClickListener {
                    val selectedText = answerViews[i].text.toString()
                    val correctAnswer = currentQuestion.correctAnswer

                    // Button aktivieren
                    vb.btnContinue.isEnabled = true
                    vb.btnContinue.visibility = View.VISIBLE

                    // Weitere Klicks sperren
                    cardViews.forEach { it.isEnabled = false }

                    if (selectedText == correctAnswer) {
                        cardViews[i].foreground = ContextCompat.getDrawable(requireContext(), R.drawable.correct_answer)
                        viewModel.rightAnswerClicked++
                        Log.i("debug", " Right Answer: ${viewModel.rightAnswerClicked}")
                        print("${viewModel.rightAnswerClicked}")
                    } else {
                        cardViews[i].foreground = ContextCompat.getDrawable(requireContext(), R.drawable.wrong_answer)
                        Log.i("debug", " wrong Answer: ${viewModel.rightAnswerClicked}")

                        // Richtige Antwort grün hervorheben
                        val correctIndex = answerViews.indexOfFirst { it.text == correctAnswer }
                        if (correctIndex != -1) {
                            cardViews[correctIndex].foreground =
                                ContextCompat.getDrawable(requireContext(), R.drawable.correct_answer)
                        }
                    }
                }
            }

            vb.btnContinue.setOnClickListener {
                viewModel.showNextQuestion {
                    findNavController().navigate(
                        QuestionFragmentDirections.actionQuestionFragmentToCompleteFragment(rightAnswers = viewModel.rightAnswerClicked, questionsAmount = args.quizObject.size )
                    )
                }

                // Farben & Button zurücksetzen
                for (i in cardViews.indices) {
                    cardViews[i].foreground = null
                    cardViews[i].isEnabled = true
                }
                vb.btnContinue.isEnabled = false
            }
        }
    }}