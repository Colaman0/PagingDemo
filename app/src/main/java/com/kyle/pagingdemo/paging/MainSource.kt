package com.kyle.pagingdemo.paging

import android.util.Log
import androidx.paging.PagingSource
import com.kyle.pagingdemo.Api
import com.kyle.pagingdemo.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Author   : kyle
 * Date     : 2020/6/15
 * Function : 分页
 */

class MainSource : PagingSource<Int, ArticleEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        // 如果key是null，那就加载第0页的数据
        val page = params.key ?: 0
        // 每一页的数据长度
        val pageSize = params.loadSize
        return try {
            delay(2000)
            val response = Api.getHomeArticles(page)
            LoadResult.Page(
                data = response.datas,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.over) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

