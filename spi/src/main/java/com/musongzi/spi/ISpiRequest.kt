package com.musongzi.spi

/*** created by linhui * on 2022/8/21 */
interface ISpiRequest {

   fun getRequestLoaderClass():Class<*>

   fun orderName():String
   fun letMeInstance(second: Class<*>?): Any? = null

}