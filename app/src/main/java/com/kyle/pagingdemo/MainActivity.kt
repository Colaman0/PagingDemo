package com.kyle.pagingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyle.pagingdemo.paging.MainSource
import com.kyle.pagingdemo.paging.PagingAdapter
import com.kyle.pagingdemo.paging.LoadmoreAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val pager by lazy {
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = { MainSource() }).flow
    }

    private val adapter by lazy {
        PagingAdapter(this)
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 给adapter添加loadmore功能
        recyclerview.adapter = adapter.withLoadStateFooter(LoadmoreAdapter())
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // 刷新adapter数据
        adapter.addLoadStateListener { loadState ->
            refreshlayout.isRefreshing = false
            // 首次加载
            if (loadState.refresh !is LoadState.NotLoading) {
                when (loadState.refresh) {
                    is LoadState.Loading -> Log.d("kyle", "首次刷新中 显示progress")
                    is LoadState.Error -> Log.d("kyle", "首次失败  显示重试按钮")
                }
            } else {
                if (loadState.prepend is LoadState.Error || loadState.append is LoadState.Error) {
                    Log.d("kyle", "加载数据失败")
                }
            }
//            when (loadState.refresh) {
//                is LoadState.Loading -> Log.d("kyle","首次刷新中")
//                is LoadState.Error -> Log.d("kyle","首次失败")
//            }
//
//            when (loadState.prepend) {
//                is LoadState.Loading -> Log.d("kyle","刷新数据中")
//                is LoadState.Error -> Log.d("kyle","刷新数据失败")
//            }
//
//            when (loadState.append) {
//                is LoadState.Loading -> Log.d("kyle","loadmore数据中")
//                is LoadState.Error -> Log.d("kyle","loadmore失败")
//            }


        }
        refreshlayout.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            pager.collect {
                // 把数据转化成itemview然后让adapter刷新ui
                adapter.submitData(it.map { ItemArticleView(it) as PagingItemView<Any> })
            }
        }
    }
}