package com.mszsupport.itf

import androidx.lifecycle.ViewModel
import com.mszsupport.comment.ViewModelSupportImpl
import com.mszsupport.itf.holder.IHolderViewModel

class CoViewModel<B : IBusiness> : ViewModel(), IHolderViewModel<B> by ViewModelSupportImpl() {
}