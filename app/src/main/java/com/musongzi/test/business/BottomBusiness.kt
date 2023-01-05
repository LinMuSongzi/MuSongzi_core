package com.musongzi.test.business

import android.view.View
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.test.vm.IBottomViewModel

class BottomBusiness: BaseMapBusiness<IBottomViewModel>() {


    override fun afterHandlerBusiness() {
        super.afterHandlerBusiness()

    }

    fun update(view : View) {

//        ahsdhfjhajsdf
    }

    fun change4Bottom(){

        iAgent.remoteData()

    }


}