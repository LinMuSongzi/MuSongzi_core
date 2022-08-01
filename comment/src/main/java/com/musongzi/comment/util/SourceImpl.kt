package com.musongzi.comment.util

import com.musongzi.core.itf.page.ISource


class SourceImpl<Item>(var datas: ArrayList<Item> = ArrayList()) : ISource<Item> {

    override fun realData() = datas
}