package com.musongzi.test.event

import com.musongzi.core.annotation.EventImpl

@EventImpl
interface ILoginEvent {

    fun onLogin();

    fun onLogout()

}