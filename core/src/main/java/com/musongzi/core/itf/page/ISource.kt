package com.musongzi.core.itf.page

interface ISource<Item> {
    fun realData(): List<Item>
}