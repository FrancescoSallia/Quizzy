package com.example.quizzy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.quizzy.data.repository.getCategoryDrawable
import com.example.quizzy.databinding.CategoryItemBinding
import com.example.quizzy.model.TriviaCategory
import com.example.quizzy.viewModel.MainViewModel


class CategorieAdapter(
    var database: List<TriviaCategory>,
    val viewModel: MainViewModel

): RecyclerView.Adapter<CategorieAdapter.CategorieViewHolder>() {
    inner class CategorieViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorieViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategorieViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return database.size
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {


     val quiz = database[position]

        holder.binding.tvCategorieName.text = quiz.name
        holder.binding.ivCategorieIcon.load(getCategoryDrawable(categoryId = quiz.id)) {
            crossfade(true)
        }

        holder.binding.cvClickable.setOnClickListener {
            viewModel.getQuizQuestions(quiz.id)
        }
    }
}