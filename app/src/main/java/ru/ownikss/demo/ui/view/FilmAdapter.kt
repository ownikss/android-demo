package ru.ownikss.demo.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ownikss.demo.R
import ru.ownikss.demo.databinding.FilmItemBinding
import ru.ownikss.demo.models.FilmModel
import ru.ownikss.demo.models.FilmStore

class FilmAdapter(private val data: ObservableField<MutableList<FilmModel>>, private val base: Fragment): RecyclerView.Adapter<FilmAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View, val binding: FilmItemBinding) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FilmAdapter.MyViewHolder {
        val b = DataBindingUtil.inflate<FilmItemBinding>(LayoutInflater.from(base.context), R.layout.film_item, parent, false)
        val store = ViewModelProviders.of(base.activity!!).get(FilmStore::class.java)
        b.store = store
        b.id = 0
        b.executePendingBindings()
        return MyViewHolder(b.root, b)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.label.text = data.get()!![position].label
        holder.binding.id = data.get()!![position].id
        val id = data.get()!![position].id

        holder.binding.label.transitionName = holder.binding.label.transitionName + id.toString()
        holder.binding.image.transitionName = holder.binding.image.transitionName + id.toString()
        holder.binding.description.transitionName = holder.binding.description.transitionName + id.toString()

        holder.binding.detailsBtn.setOnClickListener {
            holder.binding.store!!.selectFilm(holder.binding.id!!, holder.binding)
            holder.binding.executePendingBindings()
        }
        holder.binding.executePendingBindings()
        Glide.with(base)
            .load(data.get()!![position].image)
            .into(holder.binding.image)
    }

    override fun getItemCount() = data.get()!!.size
}