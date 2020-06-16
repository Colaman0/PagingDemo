package com.kyle.pagingdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kyle.pagingdemo.paging.MainSource

/**
 * Author   : kyle
 * Date     : 2020/6/16
 * Function : main viewmodel
 */

class MainViewmodel : ViewModel() {

    val pager by lazy {
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = { MainSource() }).flow.cachedIn(viewModelScope)
    }

}