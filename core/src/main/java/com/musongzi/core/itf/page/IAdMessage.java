package com.musongzi.core.itf.page;

import androidx.lifecycle.Observer;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IAdMessage<ITEM> extends Observer<List<ITEM>> {

    void load();


    void onDataStateChange(@Nullable Integer integer);
}
