package com.musongzi.core.base.vm

import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.trello.rxlifecycle4.LifecycleTransformer

/*** created by linhui * on 2022/9/15 */
class DataDriveViewModel<B:IBusiness> : CoreViewModel<IHolderActivity>() {

}