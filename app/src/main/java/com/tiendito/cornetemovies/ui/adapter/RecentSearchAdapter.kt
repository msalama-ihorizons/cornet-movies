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
import com.tiendito.cornetemovies.db.RecentSearch
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.recent_search_item.view.*

/**
 * Created by Mohamed Salama on 10/13/2020.
 */
class RecentSearchAdapter (context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<RecentSearch, OnRecyclerItemClickListener, RecentSearchAdapter.RecentSearchViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        return RecentSearchViewHolder(inflate(R.layout.recent_search_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }

    inner class RecentSearchViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<RecentSearch, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: RecentSearch) {
            itemView.keyword.text =  item.keyword
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}