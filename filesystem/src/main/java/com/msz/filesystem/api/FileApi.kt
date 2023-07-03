package com.msz.filesystem.api

import com.msz.filesystem.bean.DiskInfo
import com.msz.filesystem.bean.FileInfo
import com.msz.filesystem.bean.RespondInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FileApi {

    @FormUrlEncoded
    @POST("/disk/search")
    fun getRoot(): RespondInfo<List<DiskInfo>>

    @FormUrlEncoded
    @POST("/disk/files")
    fun getDirFiles(@Field("acceseKey") acceseKey: String, @Field("path") path: String): RespondInfo<List<FileInfo>>

//    @GET("/disk/file2")
//    @Throws(IOException::class)
//    fun loadFile(@Query("acceseKey") acceseKey: String, @Query("path") path: String): RespondInfo<Resource?>?

}