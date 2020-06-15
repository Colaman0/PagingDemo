package com.kyle.pagingdemo

import com.google.gson.annotations.SerializedName

/**
 * Author   : kyle
 * Date     : 2020/6/1
 * Function : response 基类
 */
class BaseRes<T>(
    @SerializedName("data") var responseData: T? = null,
    @SerializedName("errorCode") var errorCode: Int = 0,
    @SerializedName("errorMsg") var errorMsg: String = ""
)