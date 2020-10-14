package com.tiendito.cornetemovies.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.bmisrmovies.api.Movie
import com.tiendito.cornetemovies.R
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<Movie, OnRecyclerItemClickListener, MoviesAdapter.MovieViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflate(R.layout.movie_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }

    inner class MovieViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<Movie, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Movie) {
            itemView.movieTitle.text = item.title
            itemView.movieReleaseDate.text = item.releaseDate
            itemView.movieOverView.text = item.overview
            val posterUrl = context?.getString(R.string.movie_poster_url, item.posterPath)

            context?.let {
                Glide.with(it).load(posterUrl)
                    .into(itemView.moviePoster)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}