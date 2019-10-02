package ru.ownikss.demo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.AddFilmFragmentBinding
import ru.ownikss.demo.models.FilmStore
import ru.ownikss.demo.utils.StatusBarManager

class AddFilmFragment : Fragment() {
    lateinit var binding: AddFilmFragmentBinding

    override fun onDestroy() {
        super.onDestroy()
        binding.store!!.clearNewFilm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_film_fragment, container, false)
        binding.toolbar.setPadding(0, StatusBarManager.getHeight(context), 0, 0)
        binding.store = ViewModelProviders.of(activity!!).get(FilmStore::class.java)
        initListeners()
        return binding.root
    }

    fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        binding.addFilmBtn.setOnClickListener {
            binding.store!!.createNewFilm()
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.posterInput.getWindowToken(), 0)
            NavHostFragment.findNavController(this).popBackStack()
        }
        binding.titleInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.store!!.handleChangeNewFilmTitle(p0.toString())
            }
        })
        binding.posterInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.store!!.handleChangeNewFilmImage(p0.toString())
            }
        })
    }
}
