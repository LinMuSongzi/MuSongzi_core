package com.musongzi.test.event

import com.musongzi.core.annotation.EventImpl

@EventImpl()
interface IMusicEvent :ILoginEvent{

    fun play()


}