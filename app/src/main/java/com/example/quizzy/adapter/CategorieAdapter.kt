package com.example.quizzy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.databinding.CategoryItemBinding
import com.example.quizzy.model.Quiz

class CategorieAdapter(
    var database: List<Quiz>

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



    }
}