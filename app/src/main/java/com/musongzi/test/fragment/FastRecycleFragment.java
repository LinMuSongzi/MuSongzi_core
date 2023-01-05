package com.musongzi.test.fragment;

import static com.musongzi.comment.ExtensionMethod.toast;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.utils.ToastUtils;
import com.musongzi.comment.databinding.AdapterModelCard1Binding;
import com.musongzi.core.ExtensionCoreMethod;
import com.musongzi.core.StringChooseBean;
import com.musongzi.core.base.client.IRefreshViewClient;
import com.musongzi.core.base.fragment.QuickCollectionFragment;
import com.musongzi.core.base.fragment.ViewModelFragment;
import com.musongzi.core.base.vm.SimpleViewModel;
import com.musongzi.core.itf.page.ISource;
import com.musongzi.test.MszTestApi;
import com.musongzi.test.bean.ResponeCodeBean;
import com.musongzi.test.databinding.FragmentSoulAppTestBinding;
import com.psyone.sox.SoxProgramHandler;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

public class FastRecycleFragment  extends QuickCollectionFragment<FragmentSoulAppTestBinding, StringChooseBean, ResponeCodeBean<List<StringChooseBean>>> {

    @NonNull
    @Override
    public IRefreshViewClient createRecycleViewClient() {
        return new IRefreshViewClient() {
            @Nullable
            @Override
            public ViewGroup emptyView() {
                return null;//IRefreshViewClient.super.emptyView();
            }

            @Nullable
            @Override
            public View normalView() {
                return null;//IRefreshViewClient.super.normalView();
            }

            @Nullable
            @Override
            public RecyclerView recycleView() {
                return getDataBinding().idRecyclerView;
            }

            @Nullable
            @Override
            public SmartRefreshLayout refreshView() {
                return getDataBinding().idSmartRefreshLayout;
            }
        };
    }

    @NonNull
    @Override
    protected List<StringChooseBean> transformDataToList(ResponeCodeBean<List<StringChooseBean>> entity) {
        return entity.getData();
    }

    @Nullable
    @Override
    public Observable<ResponeCodeBean<List<StringChooseBean>>> getRemoteData(int index) {
        return ExtensionCoreMethod.getApi(MszTestApi.class,this).getArrayEngine(index, getPageEngine().pageSize(),null);
    }

    @Nullable
    @Override
    public RecyclerView.Adapter<?> getAdapter(@Nullable ISource<StringChooseBean> page) {
        return ExtensionCoreMethod.adapter(page, AdapterModelCard1Binding.class, (adapterModelCard1Binding, stringChooseBean, integer) -> {
            adapterModelCard1Binding.getRoot().setOnClickListener((v)-> {
                toast(stringChooseBean.getTitle());
               // SoxProgramHandler.exoPlaySImple(requireContext(),this,"${URL2}wavTest2.mp3");
            });
            return null;
        });
    }
}
