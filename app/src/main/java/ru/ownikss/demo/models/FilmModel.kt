package ru.ownikss.demo.models

import androidx.databinding.ObservableField

class FilmModel(val image: String, val label: String, val id: Int) {
    var comment = ObservableField<String>("")
    var isFavourite = ObservableField<Boolean>(false)
}
