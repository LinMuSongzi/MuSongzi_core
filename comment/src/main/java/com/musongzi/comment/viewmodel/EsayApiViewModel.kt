package com.musongzi.comment.viewmodel

import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.itf.IClient

/*** created by linhui * on 2022/10/18 */
class EsayApiViewModel<A>: ApiViewModel<IClient, EmptyBusiness, A>() {

    override fun indexApiActualTypeArgument(): Int {
        return 0
    }

}