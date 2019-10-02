package ru.ownikss.demo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.FilmFragmentBinding
import ru.ownikss.demo.models.FilmStore
import android.view.inputmethod.InputMethodManager
import androidx.transition.TransitionInflater
import ru.ownikss.demo.utils.StatusBarManager


class FilmFragment : Fragment() {
    lateinit var binding: FilmFragmentBinding
    val filmChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            this@FilmFragment.setFilmData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.film_fragment, container, false)
        binding.store = ViewModelProviders.of(activity!!).get(FilmStore::class.java)
        binding.appBarLayout.setPadding(0, StatusBarManager.getHeight(context), 0, 0)
        initListeners()
        setFilmData()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.store != null) {
            binding.store!!.apply {
                selectedFilm.removeOnPropertyChangedCallback(filmChangedCallback)
                closeFilm()
            }
        }
    }

    fun initListeners() {
        binding.store!!.selectedFilm.addOnPropertyChangedCallback(filmChangedCallback)
        binding.AppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        binding.container.setOnTouchListener(fun(a: View, b: MotionEvent): Boolean {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.commentInput.getWindowToken(), 0)
            return false
        })
        binding.commentInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.store!!.handleCommentChange(p0.toString())
            }
        })
    }

    fun setFilmData() {
        val film = binding.store!!.selectedFilm.get()
        if (film != null) {
            View.GONE
            binding.AppBar.title = film.label
            Glide.with(this)
                .load(film.image)
                .into(binding.toolbarImage)
        }
    }
}
