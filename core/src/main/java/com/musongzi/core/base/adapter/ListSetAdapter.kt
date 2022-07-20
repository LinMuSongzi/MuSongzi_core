package com.musongzi.core.base.adapter

import androidx.databinding.ViewDataBinding
import com.musongzi.core.ExtensionCoreMethod.entitySet

/**
 * 处理因为，itemtype 列表不一致的情况的adapter
 * @param ListItem
 * @constructor
 */
val beanSet = { d: ViewDataBinding, entity: Any ->
    d.entitySet("bean",entity.javaClass,entity)
}
abstract class ListSetAdapter<ListItem>(list: List<ListItem>) : ListAbstacyAdapter<ListItem>(list) {

    protected var defualtBeanSet = true

    final override fun convert(d: ViewDataBinding, entity: ListItem, postion: Int) {
//        Log.i("convert", ": ListSetAdapter , $postion , $d")
        if (defualtBeanSet) {
            beanSet.invoke(d, entity!!)
        }
        convertData(d, entity, postion)
    }

    open fun changeDefualtBeanSet(defualtBeanSet: Boolean): ListSetAdapter<*> {
        this.defualtBeanSet = defualtBeanSet;
        return this
    }


    abstract fun convertData(d: ViewDataBinding, entity: ListItem, postion: Int)


}