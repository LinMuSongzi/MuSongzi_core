package com.musongzi.core.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding dataBinding;

    public DataBindingViewHolder(@NonNull View itemView, ViewDataBinding dataBinding) {
        super(itemView);
        this.dataBinding = dataBinding;
    }


    public <D extends ViewDataBinding> D getDataBinding(){
        return (D) dataBinding;
    }

}
