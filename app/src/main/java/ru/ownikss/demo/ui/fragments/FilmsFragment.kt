package ru.ownikss.demo.ui.fragments;

import android.content.Intent
import android.os.Bundle;
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
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
        binding.toolbar.setNavigationOnClickListener {
            (binding.drawer as DrawerLayout).openDrawer(Gravity.LEFT)
        }
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener { this.handleMenuSelected(it) }
        binding.filmList.adapter = FilmAdapter(binding.store!!.films, this)
        binding.store!!.filmOpened.observe(activity!!, Observer {
            try {
                val extras = FragmentNavigatorExtras(
                    binding.store!!.binding!!.description to "description",
                    binding.store!!.binding!!.image to "image"
                )
                NavHostFragment.findNavController(this@FilmsFragment)
                    .navigate(R.id.action_filmsFragment_to_filmFragment, null, null, extras)
            } catch (e: Exception) {
            }
        })
        binding.drawerLayout.about.setOnClickListener {
            try {
                (binding.drawer as DrawerLayout).closeDrawer(Gravity.LEFT)
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_filmsFragment_to_aboutFragment)
            } catch (e: Exception) {
            }
        }
        return binding.root
    }

    fun handleMenuSelected(item: MenuItem): Boolean {
        if (item.title == activity!!.getString(R.string.share)) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Приглашаю")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        return false

    }
}
