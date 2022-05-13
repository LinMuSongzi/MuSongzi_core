package com.musongzi.core.base.manager.event

import java.util.*
import kotlin.collections.ArrayList

data class ClassInfoHelp(val mainClass:Class<*>) {

    var parents = ArrayList<String>()
    var instances = LinkedList<Any>()

}