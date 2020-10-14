package com.tiendito.cornetmovies.ui.discover

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.irozon.sneaker.Sneaker
import com.tiendito.cornetmovies.model.Status
import com.tiendito.cornetmovies.ui.MoviesListFragment
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * Created by Mohamed Salama on 10/13/2020.
 */

class DiscoverMoviesFragment: MoviesListFragment() {

    companion object {

        fun newInstance(): DiscoverMoviesFragment {
            val args: Bundle = Bundle()
            val fragment = DiscoverMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun handleObservers() {
        moviesViewModel.discoverMoviesListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.items = resources.data
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}