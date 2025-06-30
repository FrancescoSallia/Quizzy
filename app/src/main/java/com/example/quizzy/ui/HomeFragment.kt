package com.example.quizzy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.activityViewModels
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentHomeBinding
import kotlin.getValue

class HomeFragment : Fragment() {

    lateinit var vb: FragmentHomeBinding
//    private val viewModel: MainViewModel by activityViewModels()


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
//
//        // 3. Optional: wenn du Scale oder andere Animation willst:
//         header.scaleX = 1 - (scrollY / 10000f).coerceIn(0f, 0.2f)

//            header.scaleX = 1f
//         header.scaleY = 1 - (scrollY / 10000f).coerceIn(0f, 0.2f)

        }
    }
}