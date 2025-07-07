package com.example.quizzy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizzy.data.repository.randomPositivFeedback
import com.example.quizzy.data.repository.randomNegativFeedback
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.quizzy.R
import com.example.quizzy.data.repository.getCategoryDrawable
import com.example.quizzy.databinding.FragmentQuestionBinding
import com.example.quizzy.viewModel.MainViewModel
import org.apache.commons.text.StringEscapeUtils

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

        // Max setzen basierend auf Gesamtfragen
        vb.progressBar.max = args.quizObject.size
        vb.progressBar.progress = 1

        // Beobachte den aktuellen Index und setze Fortschritt
        viewModel.currentIndexProgressivBar.observe(viewLifecycleOwner) { index ->
            vb.progressBar.progress = index + 1 // +1, weil Index bei 0 startet
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //back button vom handy kann jetzt nicht wieder zurück navigieren!
//            findNavController().navigate(R.id.action_questionFragment_to_homeFragment)


            findNavController().navigate(QuestionFragmentDirections.actionQuestionFragmentToHomeFragment())
            viewModel.rightAnswerClicked = 0
            viewModel.resetGetQuestions()
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            // return@observe heißt:
            //→ Beende nur diese anonyme Funktion, nicht die ganze onViewCreated()-Methode. Es verlässt nur diese funktion block macht mit dem rest weiter!
            if (currentQuestion == null) return@observe

            val answerList: MutableList<String> = currentQuestion.incorrectAnswers.toMutableList()
            answerList.add(currentQuestion.correctAnswer)
            val shuffledAnswers = answerList.shuffled()

            //The Question
            vb.tvQuestion.text = viewModel.decodeText(currentQuestion.question)
            vb.tvCategoryTitle.text = viewModel.decodeText(currentQuestion.category)
            val findCategory = args.categoryList.find { category -> viewModel.decodeText(category.name) == viewModel.decodeText(currentQuestion.category) }

            if (findCategory != null){
                val categoryItem = viewModel.getCategoryFromName(findCategory.name)
                vb.ivCategoryItem.load(categoryItem)

            }

            vb.tvDifficultyLevel.text = currentQuestion.difficulty

            val colorResId = when(currentQuestion.difficulty.lowercase()) {
                "easy" -> R.color.easy_Difficulty
                "medium" -> R.color.medium_Difficulty
                "hard" -> R.color.hard_Difficulty
                else -> R.color.black
            }
            vb.tvDifficultyLevel.setTextColor(ContextCompat.getColor(requireContext(), colorResId))
            val answerViews = listOf(
                vb.tvAnswerOne,
                vb.tvAnswerTwo,
                vb.tvAnswerThree,
                vb.tvAnswerFour
            )

            val cardViews = listOf(
                vb.cvAnswerOneVisibility,
                vb.cvAnswerTwoVisibility,
                vb.cvAnswerThreeVisibility,
                vb.cvAnswerFourVisibility
            )

            // Antworten zurücksetzen
            for (i in answerViews.indices) {
                if (i < shuffledAnswers.size) {
                    answerViews[i].visibility = View.VISIBLE
                    answerViews[i].text = viewModel.decodeText(shuffledAnswers[i])
                    cardViews[i].foreground = null
                    cardViews[i].isEnabled = true
                    cardViews[i].visibility = View.VISIBLE
                } else {
                    answerViews[i].visibility = View.GONE
                    cardViews[i].visibility = View.GONE
                }
            }

            vb.btnContinue.isEnabled = false
            vb.btnContinue.visibility = View.GONE
            vb.ivFeedback.visibility = View.GONE
            vb.tvFeedback.visibility = View.GONE

            for (i in cardViews.indices) {
                cardViews[i].setOnClickListener {
                    val selectedText = answerViews[i].text.toString()
                    val correctAnswer = viewModel.decodeText(currentQuestion.correctAnswer)

                    // Button aktivieren
                    vb.btnContinue.isEnabled = true
                    vb.btnContinue.visibility = View.VISIBLE

                    // Weitere Klicks sperren
                    cardViews.forEach { it.isEnabled = false }

                    vb.tvFeedback.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_feedback_anim))
                    vb.ivFeedback.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_feedback_anim))

                    vb.ivFeedback.visibility = View.VISIBLE
                    vb.tvFeedback.visibility = View.VISIBLE

                    if (selectedText == correctAnswer) {
                        vb.tvFeedback.text = randomPositivFeedback() //Positiv Feedback
                        vb.tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.correct_answer_color))

                        vb.ivFeedback.load(R.drawable.correct_item)
                        cardViews[i].foreground = ContextCompat.getDrawable(requireContext(), R.drawable.correct_answer)
                        viewModel.rightAnswerClicked++
                    } else {
                        vb.ivFeedback.load(R.drawable.wrong_item)
                        vb.tvFeedback.text = randomNegativFeedback() //Negativ FeedBack
                        vb.tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.hard_Difficulty)) // deine Farbe

                        cardViews[i].foreground = ContextCompat.getDrawable(requireContext(), R.drawable.wrong_answer)

                        // Richtige Antwort grün hervorheben
                        val correctIndex = answerViews.indexOfFirst { it.text == correctAnswer }
                        if (correctIndex != -1) {
                            cardViews[correctIndex].foreground =
                                ContextCompat.getDrawable(requireContext(), R.drawable.correct_answer)
                        }
                    }
                }
            }

            vb.btnContinue.setOnClickListener {
                val animFromTop = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_top)
                val animAlpha = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_anim)

                if (viewModel.counterForAnimation != args.quizObject.size) {
                    vb.tvQuestion.startAnimation(animFromTop)
                    vb.tvDifficultyLevel.startAnimation(animAlpha)
                    vb.tvAnswerOne.startAnimation(animAlpha)
                    vb.tvAnswerTwo.startAnimation(animAlpha)
                    vb.tvAnswerThree.startAnimation(animAlpha)
                    vb.tvAnswerFour.startAnimation(animAlpha)
                    viewModel.counterForAnimation++
                }

                viewModel.showNextQuestion {
                    findNavController().navigate(
                        QuestionFragmentDirections.actionQuestionFragmentToCompleteFragment(rightAnswers = viewModel.rightAnswerClicked, questionsAmount = args.quizObject.size )
                    )
                }

                // Farben & Button zurücksetzen
                for (i in cardViews.indices) {
                    cardViews[i].foreground = null
                    cardViews[i].isEnabled = true
                }
                vb.btnContinue.isEnabled = false

                vb.ivFeedback.visibility = View.INVISIBLE
                vb.tvFeedback.visibility = View.INVISIBLE
                vb.tvFeedback.text = ""
                vb.ivFeedback.setImageDrawable(null)
            }
        }
    }
}