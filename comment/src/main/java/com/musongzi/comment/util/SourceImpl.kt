package com.musongzi.comment.util

import com.musongzi.core.itf.page.ISource


class SourceImpl<Item>(var datas: List<Item> = ArrayList()) : ISource<Item> {

    override fun realData() = datas
}