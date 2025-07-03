package com.example.quizzy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentCompleteBinding
import com.example.quizzy.model.User
import com.example.quizzy.viewModel.MainViewModel
import kotlin.getValue

class CompleteFragment : Fragment() {
    private val args: CompleteFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var vb: FragmentCompleteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentCompleteBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rightAnswers = args.rightAnswers
        val questionsAmount = args.questionsAmount
        val wrongAnswers = questionsAmount - rightAnswers

        vb.tvScoreResult.text = "${args.rightAnswers}/${args.questionsAmount}"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //back button vom handy kann jetzt nicht wieder zurück navigieren!
        }

        viewModel.userList.observe(viewLifecycleOwner) { users ->

            if (!users.isNullOrEmpty()) {
                for (user in users) {
                    vb.tvTotalRightAnswers.text = (user.rightAnswerList.sum() + rightAnswers).toString()
                    vb.tvTotalWrongAnswers.text = (user.wrongAnswerList.sum() + wrongAnswers).toString()
                }
            } else {
                vb.tvTotalRightAnswers.text = rightAnswers.toString()
                vb.tvTotalWrongAnswers.text = wrongAnswers.toString()
            }
        }

        vb.btnDone.setOnClickListener {
            val currentRightAnswers = rightAnswers
            val currentWrongAnswers = wrongAnswers

            // Beobachte die aktuelle Liste und aktualisiere dann
            viewModel.userList.observe(viewLifecycleOwner) { users ->
                val existingUser = users.find { it.id == 0 }

                val updatedRightAnswers =
                    existingUser?.rightAnswerList?.toMutableList() ?: mutableListOf()
                val updatedWrongAnswers =
                    existingUser?.wrongAnswerList?.toMutableList() ?: mutableListOf()

                updatedRightAnswers.add(currentRightAnswers)
                updatedWrongAnswers.add(currentWrongAnswers)

                val updatedUser = User(
                    id = 0,
                    rightAnswerList = updatedRightAnswers.toList(),
                    wrongAnswerList = updatedWrongAnswers.toList()
                )

                viewModel.insertUser(updatedUser) // <- ersetzt den bestehenden Eintrag (durch onConflict = OnConflictStrategy.REPLACE)

                viewModel.rightAnswerClicked = 0
                viewModel.resetGetQuestions()
                findNavController().navigate(CompleteFragmentDirections.actionCompleteFragmentToHomeFragment())
            }

        }
    }
}