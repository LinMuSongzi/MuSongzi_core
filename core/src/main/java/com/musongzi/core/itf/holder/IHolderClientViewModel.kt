package com.musongzi.core.itf.holder

import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient

/*** created by linhui * on 2022/9/15 */
interface IHolderClientViewModel<C : IClient?,B:IBusiness> :IHolderViewModel<B>, IHolderClient<C> {
}