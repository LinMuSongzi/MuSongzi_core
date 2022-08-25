package com.musongzi.videoplay

import android.view.SurfaceHolder
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle

/*** created by linhui * on 2022/8/25 */
internal class ShowSurfaceHandlerBusiness : BaseLifeBusiness<IHolderLifecycle>() ,IShowSurfaceHandler{



    override fun surfaceCreated(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }


}