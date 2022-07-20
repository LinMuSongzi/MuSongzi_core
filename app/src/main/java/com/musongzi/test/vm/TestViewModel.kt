package com.musongzi.test.vm

import com.musongzi.test.ITestClient
import com.musongzi.comment.viewmodel.ApiViewModel
import com.musongzi.test.Api
import com.musongzi.test.simple.TestBusiness

class TestViewModel : ApiViewModel<ITestClient, TestBusiness, Api>() {
}