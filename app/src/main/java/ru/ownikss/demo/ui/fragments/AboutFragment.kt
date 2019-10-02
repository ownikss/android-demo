package ru.ownikss.demo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.AboutFragmentBinding
import ru.ownikss.demo.utils.StatusBarManager

class AboutFragment : Fragment() {
    lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)
        binding.toolbar.setPadding(0, StatusBarManager.getHeight(context), 0, 0)
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        return binding.root
    }
}