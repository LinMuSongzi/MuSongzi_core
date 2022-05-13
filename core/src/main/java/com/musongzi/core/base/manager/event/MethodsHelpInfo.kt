package com.musongzi.core.base.manager.event

import java.lang.reflect.Method

data class MethodsHelpInfo(val method: Method) {

    val clazzs = ArrayList<ClassInfoHelp>()


}