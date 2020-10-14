package com.tiendito.cornetemovies.ui.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.irozon.sneaker.Sneaker
import com.tiendito.cornetemovies.model.Status
import com.tiendito.cornetemovies.ui.MoviesListFragment
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