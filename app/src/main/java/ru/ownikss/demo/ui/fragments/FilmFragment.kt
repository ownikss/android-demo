package ru.ownikss.demo.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.FilmFragmentBinding
import ru.ownikss.demo.models.FilmStore

class FilmFragment: Fragment() {
    lateinit var binding: FilmFragmentBinding
    val cb = object: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            this@FilmFragment.initImage()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.film_fragment, container, false)
        binding.store = ViewModelProviders.of(activity!!).get(FilmStore::class.java)
        binding.store!!.selectedFilm.addOnPropertyChangedCallback(cb)
        val film = binding.store!!.selectedFilm.get()!!
        binding.AppBar.title = film.label
        Glide.with(this)
            .load(film.image)
            .into(binding.toolbarImage)
        binding.like.setOnClickListener( {
            film.isFavourite.set(film.isFavourite.get()!!.not())
        })
        binding.commentInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                film.comment.set(p0.toString())
            }
        })
        initImage()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.store!!.selectedFilm.removeOnPropertyChangedCallback(cb)
        binding.store!!.closeFilm()
    }

    fun initImage() {
        val film = binding.store!!.selectedFilm.get()
        if (film != null) {
            Glide.with(this)
                .load(film.image)
                .into(binding.poster)
        }
    }

}