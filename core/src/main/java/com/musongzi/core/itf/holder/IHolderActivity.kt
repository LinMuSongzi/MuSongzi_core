package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.INotifyDataSetChanged
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.client.IContextClient

interface IHolderActivity : IHolderArguments<Bundle>, IHolderProviderContext, IWant, IContextClient, INotifyDataSetChanged {
    fun getHolderActivity(): FragmentActivity?

}