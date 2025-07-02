package com.example.quizzy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.quizzy.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {
    private lateinit var vb: FragmentQuestionBinding
    private val args: QuestionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentQuestionBinding.inflate(inflater,container,false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val questionList = args.quizObject.toList()
        val question = questionList[0]

        val answerList: MutableList<String> = mutableListOf()
        var i = 0

        for (answer in questionList) {
            answerList.add(answer.incorrectAnswers[i])
            i++
        }
        answerList.add(question.correctAnswer)

        vb.tvQuestion.text = question.question

        answerList.shuffled()

        vb.tvAnswerOne.text = answerList[0]
        vb.tvAnswerTwo.text = answerList[1]
        vb.tvAnswerThree.text = answerList[2]
        vb.tvAnswerFour.text = answerList[3]


    }
}