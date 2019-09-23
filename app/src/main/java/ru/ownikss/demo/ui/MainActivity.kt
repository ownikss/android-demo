package ru.ownikss.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ru.ownikss.demo.R
import ru.ownikss.demo.models.FilmStore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val store = ViewModelProviders.of(this).get(FilmStore::class.java)
        setContentView(R.layout.activity_main)
    }
}
