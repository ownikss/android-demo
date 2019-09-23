package ru.ownikss.demo.models

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import ru.ownikss.demo.utils.SingleLiveEvent

class FilmStore(application: Application) : AndroidViewModel(application) {
    val films: Array<FilmModel> = arrayOf(
        FilmModel(
            "https://s3.us-west-2.amazonaws.com/st.popcorn/movies/2a56ec/dc4a03/e7437ba7622ecf6a1780.jpg",
            "Впритык",
            1
        ),
        FilmModel("https://citaty.info/files/posters/1598.jpg", "Хэнкок", 2),
        FilmModel(
            "https://thumbs.dfs.ivi.ru/storage15/contents/c/5/5b209d015b40696307c7a180b43fd6.jpg",
            "Марсианин",
            3
        ),
        FilmModel(
            "https://yandex.ru/images/_crpd/g1FfV1598/adb7574F6/KJQXIaxxUf6B0d3UT0UskLp0618u-kWqyL4uHyxlanbjlvLJrWyVZH_gfx4TQTi1dYnnKiiYdR2uzZHXkOboHnGBPkWlVUzj3OSJjAckec2icMIeX6sQ",
            "Гений",
            4
        ),
        FilmModel(
            "https://p.kinozon.tv/%D0%BF%D0%BE%D1%81%D1%82%D0%B5%D1%80%D1%8B/24957/G_I_Joe_%D0%91%D1%80%D0%BE%D1%81%D0%BE%D0%BA_%D0%BA%D0%BE%D0%B1%D1%80%D1%8B_2-66.jpg",
            "G.I.Joe - Бросок кобры",
            5
        )
    )
    var selectedId: ObservableField<Int> = ObservableField(-1)
    var selectedFilm: ObservableField<FilmModel?> = ObservableField()
    val filmOpened = SingleLiveEvent<Any>()

    fun getSelectedFilm(): FilmModel? {
        for (i in 0 until films.size) {
            if (films[i].id == selectedId.get()) {
                return films[i]
            }
        }
        return null
    }

    fun selectFilm(id: Int) {
        selectedId.set(id)
        filmOpened.call()
    }

    fun closeFilm() {
        val film = selectedFilm.get()
        if (film != null) {
            Log.d(
                "FIRST_LESSON",
                "comment: ${film.comment.get()} + , is favourite: ${film.isFavourite.get().toString()}"
            )
        }
    }

    init {
        this.selectedId.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val film = getSelectedFilm()
                this@FilmStore.selectedFilm.set(film)
            }
        })
    }
}