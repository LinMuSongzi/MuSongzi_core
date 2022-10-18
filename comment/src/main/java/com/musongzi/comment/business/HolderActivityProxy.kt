package com.musongzi.comment.business

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.musongzi.comment.ExtensionMethod.getSaveStateValue
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.base.vm.DataDriveViewModel
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderArguments
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.util.InjectionHelp
import com.trello.rxlifecycle4.LifecycleTransformer
import java.lang.ref.WeakReference
import java.nio.file.WatchEvent

/*** created by linhui * on 2022/9/27 */
class HolderActivityProxy(activity: IHolderActivity) : IHolderActivity {

    var activityReference: WeakReference<WrapSupport>? = null

    init {
        activityReference = WeakReference(WrapSupport(activity))
    }


    override fun getHolderActivity(): FragmentActivity? {
        return "getHolderActivity".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun getClient(): IClient? {
        return "getClient".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun getHolderContext(): Context? {
        return "getHolderContext".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun putArguments(d: Bundle?) {

    }

    override fun getArguments(): Bundle? {
        return "getArguments".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun handlerArguments() {

    }

    override fun notifyDataSetChanged() {
        val index = "notifyDataSetChanged".getValue<Int>(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
        index?.let {
            "notifyDataSetChanged".setValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle(), it + 1)
        }
    }

    override fun notifyDataSetChangedItem(postiont: Int) {
        "notifyDataSetChangedItem".setValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle(), postiont)
    }

    override fun showDialog(msg: String?) {
        "showDialog".setValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle(), msg)
    }

    override fun disimissDialog() {
        val index = "disimissDialog".getValue<Int>(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
        index?.let {
            "disimissDialog".setValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle(), it + 1)
        }
    }

    override fun topViewModelProvider(): ViewModelProvider? {
        return "topViewModelProvider".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun thisViewModelProvider(): ViewModelProvider? {
        return "thisViewModelProvider".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T>? {
        return "bindToLifecycle".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun getMainLifecycle(): IHolderLifecycle? {
        return "getMainLifecycle".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun getThisLifecycle(): LifecycleOwner? {
        return "getThisLifecycle".getValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle())
    }

    override fun runOnUiThread(runnable: Runnable) {
        "notifyDataSetChangedItem".setValue(activityReference?.get()?.getInstanceModel()?.localSavedStateHandle(), runnable)
    }


    class ProxyHelpViewModel : DataDriveViewModel<EmptyBusiness>()


    class WrapSupport(var activity: IHolderActivity) {

        private var viewModelProvider = ViewModelProvider(activity.getHolderActivity()!!,
            object : AbstractSavedStateViewModelFactory(
                activity.getHolderActivity()!!,
                activity.getArguments()
            ) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return InjectionHelp.injectViewModel(
                        activity,
                        activity.getArguments(),
                        modelClass,
                        handle
                    )!!
                }
            })


        fun getInstanceModel(): ProxyHelpViewModel {
            return viewModelProvider.get(ProxyHelpViewModel::class.java)
        }
    }


    companion object {
//        fun <T> String.getLocalValue(saveStateHandle: ISaveStateHandle): T? {
//            if (support == null) {
//                return null
//            }
//            return getSaveStateValue(saveStateHandle)
//        }

        fun <T> String.getValue(saveStateHandle: ISaveStateHandle?): T? {
            if (saveStateHandle == null) {
                return null
            }
            return getSaveStateValue(saveStateHandle)
        }

//        fun <T> String.setLocalValue(support: WrapSupport?, values: T?) {
//
//            if (support == null) {
//                return
//            }
//            saveStateChange(support.getInstanceModel().localSavedStateHandle(), values)
//        }


        fun <T> String.setValue(saveStateHandle: ISaveStateHandle?, values: T?) {
            if (saveStateHandle == null) {
                return
            }
            saveStateChange(saveStateHandle, values)
        }


//        fun registerMethodCall(activity: IHolderActivity){
//
//            "getHolderActivity".setValue(activity,activity)
//
//
//        }

//        const val handlerArguments_Key = "handlerArguments"
//        const val notifyDataSetChanged_key = "notifyDataSetChanged"
//        const val notifyDataSetChangedItem_key = "notifyDataSetChangedItem"
//        const val showDialog_key = "showDialog"
//        const val disimissDialog_key = "disimissDialog"
//        const val getArguments_key = "getArguments"
    }


}