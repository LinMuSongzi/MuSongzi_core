package com.musongzi.videoplay

import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.videoplay.IShowSurfaceHandler

/*** created by linhui * on 2022/8/24  */
class PlayVideoBusiness : BaseLifeBusiness<IHolderLifecycle>(),IPlayVideoBusiness {

    var surfaceHandler: IShowSurfaceHandler? = null




}