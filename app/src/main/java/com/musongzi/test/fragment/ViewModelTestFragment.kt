package com.musongzi.test.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.musongzi.core.base.fragment.DataBindingFragment
import com.musongzi.core.base.fragment.ViewModelFragment
import com.musongzi.core.itf.IClient
import com.musongzi.test.R
import com.musongzi.test.databinding.FragmentViewModelTestBinding
import com.musongzi.test.vm.ViewModelTestViewModel

class ViewModelTestFragment : ViewModelFragment<ViewModelTestViewModel, FragmentViewModelTestBinding>() {

    companion object {
        fun newInstance() = ViewModelTestFragment()
    }

    private val observer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            dataBinding.idContentIv.text = "text = " + (sender as? ObservableField<*>)?.get()
        }
    }

    override fun initView() {
        observer.onPropertyChanged(getViewModel().initFlag,0);
        getViewModel().initFlag.addOnPropertyChangedCallback(observer)
    }

    override fun initEvent() {

    }

    override fun initData() {
        dataBinding.idContentIv.postDelayed({
            getViewModel().initFlag.set(500)
        }, 5000)
    }

//    private lateinit var viewModel by ac

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_view_model_test, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProvider(this).get(ViewModelTestViewModel::class.java)
//
//    }

}