package com.musongzi.test

import com.musongzi.core.StringChooseBean
import com.musongzi.test.bean.LoginBean
import com.musongzi.test.bean.ResponeCodeBean
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import java.io.File

/*** created by linhui * on 2022/10/11 */
interface MszTestApi {

    @POST("/login2")
    fun login2(@Body bean: LoginBean): Observable<ResponeCodeBean>

    @POST("/testUser")
    fun getArrayEngine(@Field("token") token:String, page: Int): Observable<Array<StringChooseBean>>

    @Multipart
    @POST("/postPath")
    fun postPath(@Part file:MultipartBody.Part):Observable<ResponeCodeBean>

}