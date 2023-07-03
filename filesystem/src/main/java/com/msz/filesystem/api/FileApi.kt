package com.msz.filesystem.api

import com.msz.filesystem.bean.DiskInfoI
import com.msz.filesystem.bean.FileInfo
import com.msz.filesystem.bean.RespondInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FileApi {

    @FormUrlEncoded
    @POST("/disk/search")
    fun getRoot(@Field("acceseKey") acceseKey:String): Observable<RespondInfo<List<DiskInfoI>>>

    @FormUrlEncoded
    @POST("/disk/files")
    fun getDirFiles(@Field("acceseKey") acceseKey: String, @Field("path") path: String): Observable<RespondInfo<List<FileInfo>>>

//    @GET("/disk/file2")
//    @Throws(IOException::class)
//    fun loadFile(@Query("acceseKey") acceseKey: String, @Query("path") path: String): RespondInfo<Resource?>?

}