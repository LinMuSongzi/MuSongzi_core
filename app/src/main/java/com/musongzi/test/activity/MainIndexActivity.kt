package com.musongzi.test.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import com.musongzi.comment.ExtensionMethod.toast
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.base.business.itf.IHolderSupportActivityBusiness
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.InjectionHelp
import com.musongzi.music.impl.Factory
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.test.R
import com.musongzi.test.business.MusicConfigHelpBusines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*** created by linhui * on 2022/7/20 */
class MainIndexActivity : NormalFragmentActivity() {

//    lateinit var id_title:TextView
//
////    setMainView()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setChildMainView(R.layout.activity_main)
//
//    }
////
////    private fun check() {
////        GlobalScope.launch(Dispatchers.IO) {
//////            synchronized(this@MainIndexActivity){
////                val  a = hey()
////                Log.i("MainIndexActivity", "check: $a")
//////            }
////        }
////    }
////
////    suspend fun hey():String{
////       return ""
////   }

}