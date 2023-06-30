package com.musongzi.core.itf.holder

import android.os.Bundle
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness

interface IHolderViewModel<B : IBusiness> : IAgent, IHolderBusiness<B>, IHolderAnySaveStateHandler {


}