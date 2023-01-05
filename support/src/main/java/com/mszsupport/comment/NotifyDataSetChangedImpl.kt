package com.mszsupport.comment

import com.mszsupport.itf.IClient
import com.mszsupport.itf.INotifyDataSetChanged

class NotifyDataSetChangedImpl(
    clientMethod: () -> IClient,
    var notifyDataSetChanged: (() -> Unit)? = null,
    var notifyDataSetChangedItem: ((Int) -> Unit)? = null
) : INotifyDataSetChanged {

    var client = clientMethod.invoke()

    override fun notifyDataSetChanged() {
        notifyDataSetChanged?.invoke()
    }

    override fun notifyDataSetChangedItem(postiont: Int) {
        notifyDataSetChangedItem?.invoke(postiont)
    }

    override fun showDialog(msg: String?) {
        client.showDialog(msg)
    }

    override fun disimissDialog() {
        client.disimissDialog()
    }
}