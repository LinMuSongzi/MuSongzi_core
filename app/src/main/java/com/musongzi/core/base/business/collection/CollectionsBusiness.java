package com.musongzi.core.base.business.collection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musongzi.core.ExtensionMethod;
import com.musongzi.core.base.business.BaseLifeBusiness;
import com.musongzi.core.base.vm.CollectionsViewModel;
import com.musongzi.core.itf.IDictionary;
import com.musongzi.core.itf.page.IPageEngine;
import com.musongzi.core.util.InjectionHelp;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.musongzi.core.ExtensionMethod.refreshLayoutInit;


/**
 * 一个自动适配集合的业务
 * 处理基本的业务需求
 * {@link CollectionsBusiness#instanceCollectionViewEngine(Bundle) 根据类名加载一个数据引擎}
 */
public class CollectionsBusiness extends BaseLifeBusiness<CollectionsViewModel> implements IDictionary {

    private IHolderCollections collectionsViewEngine;

    private boolean isRegisterEventBus = false;
    public boolean isOtherEmptyRes = false;
    private String mIDictionaryClass;

    public void handlerArguments(@NonNull Bundle arguments) {
        instanceCollectionViewEngine(arguments);
        checkTopTitleUI(arguments);
    }

    private void checkTopTitleUI(Bundle arguments) {
        String title = arguments.getString(ViewListPageFactory.TITLE_KEY, "");
        iAgent.emptyString = arguments.getString(ViewListPageFactory.ENABLE_EMPTY_KEY, "");
        getIAgent().getHolderClient().updateTitle(title);
    }

    private void instanceCollectionViewEngine(Bundle arguments) {
//        mIDictionaryClass;
        if (arguments == null) {
            mIDictionaryClass = iAgent.getHolderClient().engineName();
        } else {
            mIDictionaryClass = arguments.getString(ViewListPageFactory.ENGINE_KEY);
        }
//        if()
        collectionsViewEngine = iAgent.getHolderClient().getCollectionsViewEngine();

        if (collectionsViewEngine == null) {
            try {
                collectionsViewEngine = ViewListPageFactory.create(mIDictionaryClass, iAgent); //(IViewPageEngine) getClass().getClassLoader().loadClass(mIDictionaryClass).getDeclaredMethod(RecycleListFragment.method).invoke(iAgent.getClient());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "instanceCollectionViewEngine: " + collectionsViewEngine + " , " + mIDictionaryClass);
        if (!isEnableEventBus()) {
            if (isRegisterEventBus = arguments.getBoolean(ViewListPageFactory.ENGINE_EVENTBUS)) {
                EventBus.getDefault().register(collectionsViewEngine);
            }
        }
        iAgent.setTags(collectionsViewEngine.getTag());
    }

    @Override
    public void handlerView(RecyclerView r, SmartRefreshLayout s) {
//        RecycleDataBusiness.Companion.refreshLayoutInit(s, this, iAgent.getArguments().getBoolean(ViewListPageFactory.ENABLE_REFRESH_KEY), iAgent.getArguments().getBoolean(ViewListPageFactory.ENABLE_LOADMORE_KEY));
//        if (collectionsViewEngine.getLayoutManger() == null) {
//            r.setLayoutManager(new LinearLayoutManager(iAgent.getContext(), LinearLayoutManager.VERTICAL, false));
//        } else {
//            r.setLayoutManager(collectionsViewEngine.getLayoutManger());
//        }
//        r.setAdapter(new ListOkAdapter(collectionsViewEngine.realData(), collectionsViewEngine) {
//            @Override
//            public void convertData(@NotNull ViewDataBinding d, Object entity, int postion) {
//                collectionsViewEngine.convertData(d, entity, postion);
//            }
//        });

        Log.i(TAG, "handlerView: mIDictionaryClass = " + mIDictionaryClass);
        refreshLayoutInit(s, this, iAgent.getArguments().getBoolean(ViewListPageFactory.ENABLE_REFRESH_KEY, false), iAgent.getArguments().getBoolean(ViewListPageFactory.ENABLE_LOADMORE_KEY, false));
        final RecyclerView.LayoutManager l = collectionsViewEngine.getLayoutManger();
        if (l == null) {
            r.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        } else {
            r.setLayoutManager(l);
        }
        r.setAdapter(collectionsViewEngine.getAdapter());

    }

    @Override
    public void refresh() {
        getBase().refresh();
    }

    public IPageEngine<?> getBase() {
        return collectionsViewEngine.getPageSupport();
    }

    @Override
    public void next() {
        getBase().next();
    }

    @Override
    public int state() {
        return getBase().state();
    }

    @Override
    public int loadState() {
        return getBase().loadState();
    }

    @Override
    public int page() {
        return getBase().page();
    }

    @Override
    public int lastSize() {
        return getBase().lastSize();
    }

    @NotNull
    @Override
    public List realData() {
        return getBase().realData();
    }

    @Override
    public int pageSize() {
        return getBase().pageSize();
    }

    @Override
    public int thisStartPage() {
        return getBase().thisStartPage();
    }


    public void detachView() {
        if (isRegisterEventBus && !isEnableEventBus()) {
            EventBus.getDefault().unregister(collectionsViewEngine);
        }
    }

    public void handlerEmptyRes(@Nullable ViewGroup llEmpty) {
        ListEngine listEngine = InjectionHelp.findAnnotation(collectionsViewEngine.getClass(), ListEngine.class);//collectionsViewEngine.getClass().getAnnotation(ListEngine.class);
        int res;
        if (listEngine != null) {
            res = listEngine.emptyLoadRes();
//            res = iAgent.getArguments().getInt(ViewListPageFactory.ENGINE_EMPTY_LAYOUT, 0);
            if (res != 0) {
                try {
                    View view = ExtensionMethod.INSTANCE.layoutInflater(this, llEmpty, res);
                    llEmpty.removeAllViews();
                    llEmpty.addView(view);
                    collectionsViewEngine.onEmptyViewCreate(view);
                    isOtherEmptyRes = true;
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
//            else {
//                TextView textView = llEmpty.findViewById(R.id.id_main_content_tv);
//                textView.setText(listEngine.emptyString());
//            }
        }
        Log.i(TAG, "handlerEmptyRes: " + collectionsViewEngine);
        collectionsViewEngine.onEmptyViewCreate(llEmpty);
    }

    @Override
    public void registerPageIdObserver(@NotNull Observer observer) {
        getBase().registerPageIdObserver(observer);
    }
}
