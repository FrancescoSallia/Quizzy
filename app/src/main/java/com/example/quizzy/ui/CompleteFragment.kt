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
        vb = FragmentCompleteBinding.inflate(inflater,container,false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.tvScoreResult.text = "${args.rightAnswers}/${args.questionsAmount}"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //back button vom handy kann jetzt nicht wieder zurück navigieren!
        }

        vb.btnDone.setOnClickListener {
            viewModel.rightAnswerClicked = 0
            viewModel.resetGetQuestions()
            findNavController().navigate(CompleteFragmentDirections.actionCompleteFragmentToHomeFragment())
        }

    }
}