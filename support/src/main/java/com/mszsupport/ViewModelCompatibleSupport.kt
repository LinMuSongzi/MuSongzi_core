package com.mszsupport

import androidx.lifecycle.LifecycleOwner
import com.mszsupport.comment.ActivityViewSupportImpl
import com.mszsupport.itf.IActivityView

class ViewModelCompatibleSupport(lifecycle: LifecycleOwner) : IActivityView by ActivityViewSupportImpl(lifecycle) {






}