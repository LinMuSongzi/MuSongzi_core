package com.musongzi.test.vm

import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.test.ITestClient
import com.musongzi.test.simple.BaseViewModel
import com.musongzi.test.simple.TestBusiness

class TestViewModel : BaseViewModel<ITestClient, TestBusiness>() {
}