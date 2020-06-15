package com.kyle.pagingdemo

import com.google.gson.annotations.SerializedName

/**
 * Author   : kyle
 * Date     : 2020/6/2
 * Function : 分页entity
 */
data class PageDTO<T>(
    @SerializedName("curPage")
    var curPage: Int = 0,
    @SerializedName("datas")
    var datas: MutableList<T> = mutableListOf(),
    @SerializedName("offset")
    var offset: Int = 0,
    @SerializedName("over")
    var over: Boolean = true,
    @SerializedName("pageCount")
    var pageCount: Int = 0,
    @SerializedName("size")
    var size: Int = 0,
    @SerializedName("total")
    var total: Int = 0
)