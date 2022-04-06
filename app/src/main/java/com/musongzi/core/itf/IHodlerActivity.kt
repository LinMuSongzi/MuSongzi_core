package com.musongzi.core.itf

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner

interface IHodlerActivity : ILifeObject, IHodlerContext ,IArguamentHodler<Bundle>{

    fun getHodlerActivity(): AppCompatActivity?

}