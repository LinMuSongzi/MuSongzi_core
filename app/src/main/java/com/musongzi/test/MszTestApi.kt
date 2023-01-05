package com.musongzi.test

import com.musongzi.core.StringChooseBean
import com.musongzi.test.bean.LoginBean
import com.musongzi.test.bean.ResponeCodeBean
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.io.File

/*** created by linhui * on 2022/10/11 */
interface MszTestApi {


    @Streaming
    @GET
    fun grilPic(@Url path: String = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp4.itc.cn%2Fimages01%2F20210710%2Ff23b494b9ddf42ed9ad2f766f06f289c.jpeg&refer=http%3A%2F%2Fp4.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665827598&t=01718c5e9ce83a726c99c6ded2369740"): Observable<ResponseBody>


    @POST("login2")
    fun login2(@Body bean: LoginBean): Observable<ResponeCodeBean<String>>

    @FormUrlEncoded
    @POST("testArray")
    fun getArrayEngine(
        @Field("page") page: Int,
        @Field("size") size: Int?,
        @Field("lastId") lastId: String? = null
    ): Observable<ResponeCodeBean<List<StringChooseBean>>>

    @Multipart
    @POST("postPath")
    fun postPath(@Part file: MultipartBody.Part): Observable<ResponeCodeBean<String>>

}