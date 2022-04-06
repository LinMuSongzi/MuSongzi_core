package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.ILifeObject

interface IHodlerActivity : IHodlerLifecycle, IHodlerContext, IHodlerArguments<Bundle>,IHolderViewModelProvider {

    fun getHodlerActivity(): FragmentActivity?

}