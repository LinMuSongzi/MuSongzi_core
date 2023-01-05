package com.musongzi.test.fragment;

import com.musongzi.core.base.fragment.DataBindingFragment;
import com.musongzi.test.databinding.FragmentSoulAppTestBinding;

public class Fast2DataBindingFragment extends DataBindingFragment<FragmentSoulAppTestBinding> {

    @Override
    public void initView() {
        dataBinding.idTop.setText("hahhahahah");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
