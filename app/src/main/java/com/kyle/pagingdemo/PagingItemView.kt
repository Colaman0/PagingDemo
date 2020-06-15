package com.kyle.pagingdemo

import androidx.annotation.LayoutRes
import com.kyle.pagingdemo.paging.PagingVHolder

/**
 * Author   : kyle
 * Date     : 2020/6/15
 * Function : item view
 */

abstract class PagingItemView<T : Any>(@LayoutRes val layoutRes: Int) {

    lateinit var holder: PagingVHolder

    open fun onBindView(holder: PagingVHolder, position: Int) {
        this.holder = holder
    }

    abstract fun areItemsTheSame(data: T): Boolean

    abstract fun areContentsTheSame(data: T): Boolean
}