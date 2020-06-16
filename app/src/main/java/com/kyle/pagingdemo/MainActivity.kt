package com.kyle.pagingdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.colaman.statuslayout.StatusConfig
import com.colaman.statuslayout.StatusLayout
import com.kyle.pagingdemo.paging.LoadmoreAdapter
import com.kyle.pagingdemo.paging.PagingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val viewmodel by lazy {
        ViewModelProvider(this).get(MainViewmodel::class.java)
    }

    private val adapter by lazy {
        PagingAdapter(this)
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initStatus()

        // 给adapter添加loadmore功能
        recyclerview.adapter = adapter.withLoadStateFooter(LoadmoreAdapter { adapter.retry() })
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.addDataRefreshListener {
            refreshlayout.isRefreshing = false
        }
        adapter.addLoadStateListener { loadState ->
            // 这里的逻辑可以自由发挥
            when (loadState.refresh) {
                is LoadState.Error -> statuslayout.switchLayout(StatusLayout.STATUS_ERROR)
                is LoadState.Loading -> {
                    statuslayout.showDefaultContent()
                    refreshlayout.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    statuslayout.showDefaultContent()
                    refreshlayout.isRefreshing = false
                }
            }
        }

        refreshlayout.setOnRefreshListener {
            // 刷新数据
            adapter.refresh()
        }

        lifecycleScope.launch {
            viewmodel.pager.collect {
                // 把数据转化成itemview然后让adapter刷新ui
                adapter.submitData(it.map { ItemArticleView(it) as PagingItemView<Any> })
            }
        }
    }

    /**
     * 初始化statuslayout
     *
     */
    fun initStatus() {
        statuslayout.add(StatusConfig(StatusLayout.STATUS_LOADING, R.layout.layout_loading))
        statuslayout.add(
            StatusConfig(
                StatusLayout.STATUS_ERROR,
                R.layout.layout_error,
                clickRes = R.id.btn_retry
            )
        )
        statuslayout.setLayoutClickListener(object : StatusLayout.OnLayoutClickListener {
            override fun OnLayoutClick(view: View, status: String?) {
                if (status === StatusLayout.STATUS_ERROR) {
                    adapter.retry()
                }
            }
        })
    }
}