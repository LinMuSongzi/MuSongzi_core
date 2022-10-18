package com.musongzi.core.base.business.collection;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.musongzi.core.ExtensionCoreMethod;
import com.musongzi.core.base.business.BaseWrapBusiness;
import com.musongzi.core.itf.IBusiness;
import com.musongzi.core.itf.IViewInstance;
import com.musongzi.core.itf.page.ISource;

import org.jetbrains.annotations.NotNull;

import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/*** created by linhui * on 2022/9/23 */
public class HeadSupportByRecycleBusiness extends BaseWrapBusiness<IViewInstance> {


    public HeadSupportByRecycleBusiness(@Nullable IBusiness dependBusiness) {
        super(dependBusiness);
    }

    public HeadSupportByRecycleBusiness() {
    }


//    @NotNull
//    public <D extends ViewDataBinding, I extends ISource<I>> RecyclerView.Adapter<?> instanceAdapter(@NotNull ISource<I> source, @NotNull Class<D> dataBindingClass
//            , Function3<ViewDataBinding, I, Integer, Unit> function3) {
//        Function1<Integer, Unit> function = new Function1<Integer, Unit>() {
//            @Override
//            public Unit invoke(Integer integer) {
//
//
//                return null;
//            }
//        };
//        RecyclerView.Adapter<?> adapter = ExtensionCoreMethod.adapter(source, , dataBindingClass,
//                (d, integer) -> {
//
//
//                    return null;
//                }
//                , function3);
//        return adapter;
//    }
}
