package com.example.quizzy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            val answerList: MutableList<String> = currentQuestion.incorrectAnswers.toMutableList()
            answerList.add(currentQuestion.correctAnswer)

            answerList.shuffled()

            vb.tvQuestion.text = currentQuestion.question


            // Setze Antworten nur wenn vorhanden
            val answerViews = listOf(
                vb.tvAnswerOne,
                vb.tvAnswerTwo,
                vb.tvAnswerThree,
                vb.tvAnswerFour
            )

            for (i in answerViews.indices) {
                if (i < answerList.size) {
                    answerViews[i].visibility = View.VISIBLE
                    answerViews[i].text = answerList[i]
                } else {
                    answerViews[i].visibility = View.GONE
                    vb.cvAnswerThreeVisibility.visibility = View.GONE
                    vb.cvAnswerFourVisibility.visibility = View.GONE
                }
            }

            vb.btnContinue.setOnClickListener {

                viewModel.showNextQuestion(){
                    findNavController()
                        .navigate(
                            QuestionFragmentDirections
                                .actionQuestionFragmentToCompleteFragment()
                        )
                }
            }

        }
    }
}