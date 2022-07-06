package com.musongzi.core.bean

import com.musongzi.core.base.vm.ActivityHelpViewModel

/*** created by linhui * on 2022/7/5 */
//@Parcelize
data class ActivityHelpInfo(
    var helpModelName: String = ActivityHelpViewModel::class.java.name,
)