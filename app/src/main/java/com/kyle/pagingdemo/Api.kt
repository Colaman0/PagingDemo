package com.kyle.pagingdemo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author   : kyle
 * Date     : 2020/6/15
 * Function : api
 */
object Api {
    private val retrofitApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApi::class.java)

    }

    suspend fun getHomeArticles(page: Int): PageDTO<ArticleEntity> {
        return retrofitApi.getHomeArticles(page).responseData!!
    }

}

