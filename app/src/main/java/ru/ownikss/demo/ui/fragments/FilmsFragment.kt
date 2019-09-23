package ru.ownikss.demo.ui.fragments;

import android.content.Intent
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.FilmsFragmentBinding
import ru.ownikss.demo.models.FilmStore
import ru.ownikss.demo.ui.view.FilmAdapter

class FilmsFragment : Fragment() {
    lateinit var binding: FilmsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.films_fragment, container, false)
        binding.store = ViewModelProviders.of(activity!!).get(FilmStore::class.java)
        binding.filmList.layoutManager = LinearLayoutManager(context)
        binding.filmList.adapter = FilmAdapter(binding.store!!.films, this)
        binding.store!!.filmOpened.observe(activity!!, Observer {
            try {
                NavHostFragment.findNavController(this@FilmsFragment)
                    .navigate(R.id.action_filmsFragment_to_filmFragment)
            } catch (e: Exception) {
            }
        })
        binding.shareBtn.setOnClickListener({
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Приглашаю")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        })
        return binding.root
    }
}
