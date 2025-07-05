package com.example.quizzy.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizzy.R
import com.example.quizzy.databinding.FragmentCompleteBinding
import com.example.quizzy.databinding.FragmentProgressBinding
import com.example.quizzy.viewModel.MainViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.getValue

class ProgressFragment : Fragment() {
    private lateinit var vb: FragmentProgressBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentProgressBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.ivBackArrowProgress.setOnClickListener {
            findNavController().navigate(ProgressFragmentDirections.actionProgressFragmentToHomeFragment())
        }
        vb.tvBackText.setOnClickListener {
            findNavController().navigate(ProgressFragmentDirections.actionProgressFragmentToHomeFragment())
        }

        viewModel.userList.observe(viewLifecycleOwner) { users ->

            users.forEach { user ->
                setupBarChart(
                    correctAnswersList = user.rightAnswerList,
                    wrongAnswersList = user.wrongAnswerList
                )
                showPercentage(
                    correctAnswersList = user.rightAnswerList,
                    wrongAnswersList = user.wrongAnswerList
                )
                vb.progressRightAnswerTotal.text = user.rightAnswerList.sum().toString()
                vb.progressWrongAnswerTotal.text = user.wrongAnswerList.sum().toString()
            }
        }

        vb.btnResetProgress.setOnClickListener {
            //TODO: Hier kommt die lösch alles vom User funktion(Room-Database)
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
        vb.progressPercentage.text = "$formatted%"
    }

    //BarChart(Column)
    private fun setupBarChart(correctAnswersList: List<Int>, wrongAnswersList: List<Int>) {
        // Es werden zwei Balken pro X-Wert (Quiznummer) angezeigt: richtig und falsch
        val barEntriesCorrect = correctAnswersList.mapIndexed { index, value ->
            com.github.mikephil.charting.data.BarEntry(index.toFloat(), value.toFloat())
        }
        val barEntriesWrong = wrongAnswersList.mapIndexed { index, value ->
            com.github.mikephil.charting.data.BarEntry(index.toFloat(), value.toFloat())
        }
        val correctDataSet = com.github.mikephil.charting.data.BarDataSet(barEntriesCorrect, "Right Answers").apply {
            color = Color.GREEN
            valueTextColor = Color.GREEN
        }
        val wrongDataSet = com.github.mikephil.charting.data.BarDataSet(barEntriesWrong, "Wrong Answers").apply {
            color = Color.RED
            valueTextColor = Color.RED
        }
        // Beide DataSets in eine BarData
        val barData = com.github.mikephil.charting.data.BarData(correctDataSet, wrongDataSet)
        // Balken nebeneinander gruppieren
        val groupSpace = 0.2f
        val barSpace = 0.05f
        val barWidth = 0.35f
        barData.barWidth = barWidth

        vb.barChart.apply {
            data = barData
            description.text = "Answers Bar Chart"
            axisRight.isEnabled = false
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
            legend.isEnabled = true
            setFitBars(true)
            // Gruppierung der Balken
            val groupCount = correctAnswersList.size.coerceAtLeast(wrongAnswersList.size)
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 0f + groupCount
            groupBars(0f, groupSpace, barSpace)
            animateY(1000)
            invalidate()
        }
    }
}
