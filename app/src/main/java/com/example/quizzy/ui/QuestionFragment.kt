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

        val answerList: MutableList<String> = question.incorrectAnswers.toMutableList()
        answerList.add(question.correctAnswer)

        answerList.shuffled()


        vb.tvQuestion.text = question.question


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



//        vb.tvAnswerOne.text = answerList[0]
//        vb.tvAnswerTwo.text = answerList[1]
//        vb.tvAnswerThree.text = answerList[2]
//        vb.tvAnswerFour.text = answerList[3]


    }
}