package com.example.quizzy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentCompleteBinding
import com.example.quizzy.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    private lateinit var vb: FragmentProgressBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentProgressBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}