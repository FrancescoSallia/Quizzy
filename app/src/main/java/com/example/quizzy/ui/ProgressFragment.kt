package com.example.quizzy.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentCompleteBinding
import com.example.quizzy.databinding.FragmentProgressBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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

        val correctAnswersList = listOf(2,0,1,2,0,0)
        val wrongAnswersList = listOf(3,5,4,3,5,5)

        setupChart(correctAnswersList = correctAnswersList, wrongAnswersList = wrongAnswersList )
        showPercentage(correctAnswersList = correctAnswersList, wrongAnswersList = wrongAnswersList)

    }

    private fun setupChart(correctAnswersList: List<Int>, wrongAnswersList: List<Int>) {
        // X-Achse: z.B. Quiznummer 0,1,2...
        val correctEntries = correctAnswersList.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }
        val wrongEntries = wrongAnswersList.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val correctDataSet = LineDataSet(correctEntries, "Richtige Antworten").apply {
            color = Color.GREEN
            valueTextColor = Color.GREEN
            lineWidth = 3f
            circleRadius = 5f
            setCircleColor(Color.GREEN)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val wrongDataSet = LineDataSet(wrongEntries, "Falsche Antworten").apply {
            color = Color.RED
            valueTextColor = Color.RED
            lineWidth = 3f
            circleRadius = 5f
            setCircleColor(Color.RED)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val lineData = LineData(correctDataSet, wrongDataSet)
        vb.lineChart.apply {
            data = lineData
            description.text = "Antworten Verlauf"
            animateX(1000)
            axisRight.isEnabled = false
            xAxis.granularity = 1f
            legend.isEnabled = true
            invalidate()
        }
    }

    private fun showPercentage(correctAnswersList: List<Int>, wrongAnswersList: List<Int>) {
        val totalCorrect = correctAnswersList.sum()
        val totalWrong = wrongAnswersList.sum()
        val total = totalCorrect + totalWrong

        val percentCorrect = if (total > 0) {
            (totalCorrect.toFloat() / total.toFloat()) * 100
        } else 0f

        val formatted = String.format("%.1f", percentCorrect)
        vb.progressPercentage.text = "Richtig: $formatted%"
    }
}
