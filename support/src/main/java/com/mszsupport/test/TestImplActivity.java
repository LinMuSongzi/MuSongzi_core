package com.mszsupport.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.mszsupport.comment.ActivityViewSupportImpl;
import com.mszsupport.itf.IActivityView;
import com.mszsupport.itf.holder.IHolderActivityView;
import com.mszsupport.itf.holder.IHolderActivityViewKt;

public class TestImplActivity extends FragmentActivity implements IHolderActivityView {

    private IActivityView activityView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityView = new ActivityViewSupportImpl(this,savedInstanceState);

//        MyViewModel viewModel = activityView.thisViewModelProvider().get(MyViewModel.class);
//        viewModel.getHolderSavedStateHandle().getLiveData<String>("haha").observe();

    }

    @Nullable
    @Override
    public IActivityView getHolderActivityView() {
        return IHolderActivityViewKt.getSafeAtivityView(this);
    }
}
