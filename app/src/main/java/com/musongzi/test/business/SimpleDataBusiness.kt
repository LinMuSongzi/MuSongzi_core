package com.musongzi.test.business

import java.util.*
import java.util.concurrent.TimeUnit

/*** created by linhui * on 2022/8/24 */
class SimpleDataBusiness : DataBusiness<Int>() {


    override fun loadData(): Int {
        TimeUnit.SECONDS.sleep(5)
        return 1
    }



}