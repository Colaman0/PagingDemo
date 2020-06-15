package com.kyle.pagingdemo

import retrofit2.http.*

/**
 * Author   : kyle
 * Date     : 2019/10/14
 * Function : api接口
 */
interface IApi {

    /**
     * 获取首页文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): BaseRes<PageDTO<ArticleEntity>>
}