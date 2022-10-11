package com.musongzi.test

import com.musongzi.test.bean.LoginBean
import com.musongzi.test.bean.ResponeCodeBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/*** created by linhui * on 2022/10/11 */
interface MszTestApi {

    @POST("/login2")
    fun login2(@Body bean: LoginBean): Observable<ResponeCodeBean>

}