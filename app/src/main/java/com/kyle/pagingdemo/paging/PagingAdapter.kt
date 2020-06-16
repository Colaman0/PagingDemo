package com.kyle.pagingdemo.paging

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kyle.pagingdemo.PagingItemView
import com.kyle.pagingdemo.R
import com.kyle.pagingdemo.databinding.ItemLoadmoreBinding

/**
 * Author   : kyle
 * Date     : 2020/6/15
 * Function : pagingadapter
 */
class PagingAdapter(context: Context) :
    PagingDataAdapter<PagingItemView<Any>, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<PagingItemView<Any>>() {
            override fun areItemsTheSame(
                oldItem: PagingItemView<Any>,
                newItem: PagingItemView<Any>
            ): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(
                oldItem: PagingItemView<Any>,
                newItem: PagingItemView<Any>
            ): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }
    ) {

    val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            getItem(position)?.onBindView(holder = holder as PagingVHolder, position = position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.layoutRes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagingVHolder(layoutInflater.inflate(viewType, parent, false))
    }
}

class LoadmoreAdapter(val retrycallback: () -> Unit) : LoadStateAdapter<LoadmoreView>() {
    override fun onBindViewHolder(holder: LoadmoreView, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadmoreView {
        return LoadmoreView(parent, loadState, retrycallback)
    }

}

class LoadmoreView(
    parent: ViewGroup,
    loadState: LoadState,
    val retrycallback: () -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)!!.inflate(R.layout.item_loadmore, parent, false)
    ) {

    val loading = ObservableBoolean()


    init {
        DataBindingUtil.bind<ItemLoadmoreBinding>(itemView)?.itemview = this
        bindState(loadState)
    }

    fun bindState(loadState: LoadState) {
        loading.set(loadState is LoadState.Loading)
    }
}

class PagingVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
