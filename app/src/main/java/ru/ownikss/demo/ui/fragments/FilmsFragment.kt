package ru.ownikss.demo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment;
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.FilmsFragmentBinding

class FilmsFragment: Fragment() {
    lateinit var binding: FilmsFragmentBinding

    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.films_fragment, container, false)
        return binding.root
    }
}
