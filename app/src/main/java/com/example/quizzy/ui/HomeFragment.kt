package com.example.quizzy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizzy.R
import com.example.quizzy.adapter.CategorieAdapter
import com.example.quizzy.databinding.FragmentHomeBinding
import com.example.quizzy.viewModel.MainViewModel
import kotlin.getValue

class HomeFragment : Fragment() {

   private lateinit var vb: FragmentHomeBinding
   private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentHomeBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val scrollView = vb.scrollView
        val overlay = view.findViewById<View>(R.id.overlay)
        val header = vb.headerLayout

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY

            // 1. Overlay-Fade
            val alpha = (scrollY / 100f).coerceIn(0f, 1f) * 0.5f
            overlay.alpha = alpha

            // 2. Parallax-Effekt oder mitziehendes Verhalten
            header.translationY = scrollY * 0.5f

        }
        viewModel.categories.observe(viewLifecycleOwner) { quiz ->
            vb.rvCategories.adapter = CategorieAdapter(database = quiz, viewModel)
        }

        viewModel.getCategories()
//        viewModel.getRandomQuizes()
    }
}