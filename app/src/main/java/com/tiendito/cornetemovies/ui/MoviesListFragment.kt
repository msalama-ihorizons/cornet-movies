package com.tiendito.cornetemovies.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.cornetemovies.R
import com.tiendito.cornetemovies.ui.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*


/**
 * Created by Mohamed Salama on 10/13/2020.
 */
abstract class MoviesListFragment: Fragment()  {

    protected val moviesViewModel: MoviesViewModel by activityViewModels()
    protected lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MoviesAdapter(
            context,
            OnRecyclerItemClickListener {
            })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        moviesRV.adapter =  moviesAdapter

        handleObservers()
    }


    abstract fun handleObservers()
}