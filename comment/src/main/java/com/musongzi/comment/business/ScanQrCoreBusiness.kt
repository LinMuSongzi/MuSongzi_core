package com.musongzi.comment.business

import com.musongzi.comment.ExtensionMethod.toast
import com.musongzi.comment.business.PermissionHelpBusiness.Companion.quickRequestCamera
import com.musongzi.comment.business.PermissionHelpBusiness.Companion.quickRequestPermission
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.*
import java.security.Permission

/*** created by linhui * on 2022/8/16 */
class ScanQrCoreBusiness : BaseMapBusiness<IViewInstance>() {


    fun startScan(){
        quickRequestCamera({
            toast("无法开启相机")
        }){




        }
    }


}