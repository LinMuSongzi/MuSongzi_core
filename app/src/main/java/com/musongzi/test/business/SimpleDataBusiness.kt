package com.musongzi.test.business

import com.musongzi.core.base.business.RemoteAsynLoadBusiness
import java.util.concurrent.TimeUnit

/*** created by linhui * on 2022/8/24 */
class SimpleDataBusiness : RemoteAsynLoadBusiness<Int>() {


    override fun loadData(): Int {
        TimeUnit.SECONDS.sleep(5)
        return 1
    }



}