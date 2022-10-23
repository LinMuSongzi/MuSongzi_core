package com.mszsupport.itf.page

interface ISource<Item> {
    fun realData(): List<Item>
}