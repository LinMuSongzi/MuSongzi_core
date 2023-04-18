package com.musongzi.test.vm

import android.util.Log
import android.webkit.WebViewClient
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.base.vm.ClientViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderActivity

class ViewModelTestViewModel : ClientViewModel<IClient, EmptyBusiness>() {

}