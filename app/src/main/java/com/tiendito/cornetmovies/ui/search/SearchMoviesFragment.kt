package com.tiendito.cornetmovies.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irozon.sneaker.Sneaker
import com.tiendito.cornetmovies.R
import com.tiendito.cornetmovies.model.Status
import com.tiendito.cornetmovies.ui.MoviesListFragment
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * Created by Mohamed Salama on 10/13/2020.
 */
class SearchMoviesFragment : MoviesListFragment() {

    companion object {
        fun newInstance(): SearchMoviesFragment {
            val args: Bundle = Bundle()
            val fragment = SearchMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        moviesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == moviesAdapter.itemCount - 1) {
                    moviesViewModel.loadNextPage()
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val recentSearchesBottomSheet = RecentSearchesBottomSheet()
                recentSearchesBottomSheet.recentSearchClickCallback =
                    object :
                        RecentSearchesBottomSheet.RecentSearchClickCallback {
                        override fun onRecentSearchSelected(keyWord: String) {
                            moviesAdapter.clear()
                            moviesViewModel.searchMovies(searchKeyword = keyWord)
                        }

                    }
                recentSearchesBottomSheet.show(childFragmentManager, "rateBottomSheetFragment")
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun handleObservers() {
        moviesViewModel.searchMoviesListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.addAll(resources.data)
                Status.ERROR -> Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message ?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}