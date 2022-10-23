package com.mszsupport.itf.holder

import com.mszsupport.itf.IBusiness
import com.mszsupport.itf.IClient

/*** created by linhui * on 2022/9/15 */
interface IHolderClientViewModel<C : IClient?,B: IBusiness> : IHolderViewModel<B>, IHolderClient<C> {
}