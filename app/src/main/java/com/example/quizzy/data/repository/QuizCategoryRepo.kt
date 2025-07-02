package com.example.quizzy.data.repository

import com.example.quizzy.R

fun getCategoryDrawable(categoryId: Int, categoryName: String? = null): Int {
    return when (categoryId) {
        9 -> R.drawable.denken       // General Knowledge
        10 -> R.drawable.buch   // Entertainment: Books
        11 -> R.drawable.movie_player    // Entertainment: Film
        12 -> R.drawable.musik_note   // Entertainment: Music
        13 -> R.drawable.theater // Entertainment: Musicals & Theatres
        14 -> R.drawable.fernsehen      // Entertainment: Television
        15 -> R.drawable.game // Entertainment: Video Games
        16 -> R.drawable.schach // Entertainment: Board Games
        17 -> R.drawable.atom        // Science & Nature
        18 -> R.drawable.computer     // Science: Computers
        19 -> R.drawable.mathematik   // Science: Mathematics
        20 -> R.drawable.spiralgalaxie              // Mythology
        21 -> R.drawable.hantel                 // Sports
        22 -> R.drawable.geography_img              // Geography
        23 -> R.drawable.historische_seite                // History
        24 -> R.drawable.politics               // Politics
        25 -> R.drawable.art                    // Art
        26 -> R.drawable.celebrities            // Celebrities
        27 -> R.drawable.animals                // Animals
        28 -> R.drawable.vehicles               // Vehicles
        29 -> R.drawable.entertainment_comics  // Entertainment: Comics
        30 -> R.drawable.science_gadgets        // Science: Gadgets
        31 -> R.drawable.entertainment_anime    // Entertainment: Japanese Anime & Manga
        32 -> R.drawable.entertainment_cartoon  // Entertainment: Cartoon & Animations
        else -> R.drawable.default_category     // Default fallback image
    }
}