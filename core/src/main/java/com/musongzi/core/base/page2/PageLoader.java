package com.musongzi.core.base.page2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.musongzi.core.base.page.ICataloguePage2;
import com.heart.core.base.page.PageArrayList;
import com.heart.core.base.page2.PageCallBack;
import com.heart.core.base.page2.RemoteObserver;
import com.heart.core.base.page2.RequestObservableBean;
import com.heart.core.base.page2.StateInfo;
import com.musongzi.core.itf.holder.IHolderCheckDataEnd;
import com.heart.core.itf.page.ICheckDataEnd;
import com.musongzi.core.itf.page.IHolderOnDataChangeListener;
import com.musongzi.core.itf.page.IHolderOnDataChangeListener2;
import com.musongzi.core.itf.page.ILimitOnLoaderState;
import com.heart.core.itf.page.ILimitRead;
import com.musongzi.core.itf.page.IPageEngine;
import com.musongzi.core.itf.page.IPageEngine2;
import com.heart.core.itf.page.OnPageDataChange;
import com.heart.core.util.MainLiveData;
import com.heart.core.util.UiUtilKt;
import com.psy1.cosleep.library.base.OttoBus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class PageLoader<ListItem, DataEntity> implements ICataloguePage2<ListItem,DataEntity>,
        ILimitRead, ILimitOnLoaderState, IHolderCheckDataEnd, IHolderOnDataChangeListener2<ListItem, RequestObservableBean<DataEntity>>,
        IState2, IHolderOnDataChangeListener<ListItem> {
    private final List<OnPageDataChange2<ListItem, RequestObservableBean<DataEntity>>> mOnPageDataChanges2 = new LinkedList<>();
    private final List<OnPageDataChange<ListItem>> mOnPageDataChanges = new LinkedList<>();
    @Nullable
    private Listener listener = null;

    private int maxCount = -1;
    private static final String TAG = "PageLoader";

    private final Set<RemoteObserver<DataEntity>> mRemoteObservers = new HashSet<>();

    /**
     * 分页的最基本的数据存储原型
     */
    private final List<ListItem> data = new PageArrayList<>();
//    /**
//     * 是否正在加载
//     */
//    private boolean isLoaderNow = false;
    /**
     * 一个提供给外部实现的回调函数
     * 用户处理不同业务情况
     */
    @NonNull
    protected PageCallBack<ListItem, DataEntity> pageCallBack;
    /**
     * 最后一次的数据长度
     */
    private int lastSize = 0;
    private int maxPage = -1;
    public boolean isEndPage = false;
    private ICheckDataEnd checkEndDatasFunction;
    /**
     * 状态
     */
    public final MutableLiveData<Integer> state = new MainLiveData<>();

    /**
     * 状态2
     */
    public final MutableLiveData<StateInfo> stateInfo = new MainLiveData<>();
    /**
     * 此size不是数据长度，是数据更新的次数
     */
    private final MutableLiveData<RequestObservableBean<DataEntity>> mRequestObservableLivedata = new MainLiveData<>();
    /**
     * page 当前的页数。不一定第一页就是0或者1
     */
    private final MutableLiveData<Integer> page = new MainLiveData<>();
    /**
     * 识别一次加载 load->next 完整id，但是未想好写进代码里
     */
    private final MutableLiveData<Integer> loadId = new MainLiveData<>(0);

//    /**
//     * 包裹的一个静态代理
//     */
//    @Deprecated
//    private Observer<DataEntity> mFlashObserver;
    /**
     * 准备刷新时候，会被赋值。标记当前刷新时间
     */
    private long thisRefreshTime = 0;
    /**
     * 准备下一页加载更多时候，会被赋值。标记当前记载更多的时间
     */
    private long thisNextTime = 0;
    /**
     * 刷新的时间的间隔阚值
     */
    private long thisRefreshTimeLimite = 1500;
    /**
     * 加载更多数据的时间的间隔阚值
     */
    private long thisNextTimeLimite = 1500;

    private boolean enableRefreshLimit = false;
    private boolean enableNextLoadLimit = false;

    private int loaderCount = 0;

    /**
     * 观察到网络/数据库数据变化
     * {@link RemoteObserver}
     */
    final Observer<RequestObservableBean<DataEntity>> nativeOberver = new Observer<RequestObservableBean<DataEntity>>() {

        @Override
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

        }

        /**
         * 基于{@link Observer} 观察者回调，当数据回调时
         * 其中判断一些基本参数，是否时刷新，是否时分页
         * 然后调用{@link  PageCallBack#transformDataToList(Object)} )来转化数据为集合的 {@link #}泛型类型
         *
         * @param entity
         */
        @Override
        public void onNext(RequestObservableBean<DataEntity> entity) {
            int mode = pageCallBack.getBusinessMode();
            Log.d(TAG, "onNext: getBusinessMode = " + SIMPLE_MODE);

            switch (mode) {
                case POSTION_MODE:
                    postionModeBusiness(entity);
                    break;
                default:
                    simpleModeBusiness(entity);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError: " + e.getMessage());
            Log.d(TAG, "onError: PageCallBack . " + Arrays.toString(e.getStackTrace()));
//        isLoaderNow = false;
            state.setValue(STATE_END_ERROR);
        }

        @Override
        public void onComplete() {

        }
    };


    public long getThisRefreshTimeLimite() {
        return thisRefreshTimeLimite;
    }

    public void setThisRefreshTimeLimite(long thisRefreshTimeLimite) {
        this.thisRefreshTimeLimite = thisRefreshTimeLimite;
    }

    public long getThisNextTimeLimite() {
        return thisNextTimeLimite;
    }

    public void setThisNextTimeLimite(long thisNextTimeLimite) {
        this.thisNextTimeLimite = thisNextTimeLimite;
    }

    PageLoader() throws Exception {
        throw new Exception("不允许初始化,请使用带参数的构造方法 PageLoader(CallBack<ListItem, DataEntity> callBack) ");
    }


    /**
     * pageloader 唯一实例化方式
     *
     * @param callBack 回调
     * @param <I>      item数据
     * @param <D>      data网络数据
     * @return
     */
    public static <I, D> IPageEngine2<I,D> createInstance(@NonNull PageCallBack<I, D> callBack) {
        return new PageLoader<>(callBack);
    }


    private PageLoader(PageCallBack<ListItem, DataEntity> pageCallBack) {
//        this.data = data;
        //保存回调变量
        this.pageCallBack = pageCallBack;
        LifecycleOwner lifecycleOwner = pageCallBack.getThisLifecycle();
        //设置刷新/加载更多回调
        if (lifecycleOwner == null) {
            page.observeForever(page -> {
                Log.d(TAG, "PageLoder: observeForever  = " + page);
                load(page);
            });
        } else {
            page.observe(lifecycleOwner, page -> {
                Log.d(TAG, "PageLoder: observe  = " + page);
                load(page);
            });
        }

        //设置状态改变回调
        if (lifecycleOwner == null) {
            state.observeForever(pageCallBack::handlerState);
        } else {
            state.observe(lifecycleOwner, pageCallBack::handlerState);
        }

        //设置请求成功后的处理数据回调
        androidx.lifecycle.Observer<RequestObservableBean<DataEntity>> observer = request -> {
            //分发监听数据变化事件
            dispatchDataChange(data, request);
            //回调函数处理回调
            pageCallBack.handlerDataChange(data, request);
            //已过期；会调用回调的方法去发送一个otto订阅对象，前提是post不为空
            Object post = pageCallBack.createPostEvent(request);
            if (post != null) {
                OttoBus.getInstance().post(post);
            }
        };
        //订阅处理数据回调
        if (lifecycleOwner == null) {
            mRequestObservableLivedata.observeForever(observer);
        } else {
            mRequestObservableLivedata.observe(lifecycleOwner, observer);
        }
        /**
         * 这里单独扩展了广告情况的处理
         * 目前未测试未使用
         */
        if (lifecycleOwner != null && pageCallBack.getAdMessage() != null) {
            lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
                @Override
                public void onCreate(@NonNull LifecycleOwner owner) {
                    //当如果广告不为空的时候，加载广告
                    pageCallBack.getAdMessage().load();
                    //也注册一个请求成功数据即将改变时观察者
                    mRequestObservableLivedata.observe(owner, integer -> {
                        pageCallBack.getAdMessage().onChanged(data);
                    });
                    owner.getLifecycle().removeObserver(this);
                }
            });
            //也注册一个状态改变时观察者
            state.observe(lifecycleOwner, mState -> pageCallBack.getAdMessage().onDataStateChange(mState));
        }
    }

    /**
     * 分发监听数据源变化监听器
     *
     * @param data                  要判断和分发的数据
     * @param requestObservableBean 这次请求的对应数据
     */
    private void dispatchDataChange(List<ListItem> data, RequestObservableBean<DataEntity> requestObservableBean) {


        Iterator<OnPageDataChange2<ListItem, RequestObservableBean<DataEntity>>> iterator = mOnPageDataChanges2.iterator();
        while (iterator.hasNext()) {
            try {
                OnPageDataChange2<ListItem, RequestObservableBean<DataEntity>> change = iterator.next();
                Log.d(TAG, "checkOnDataChange: change = " + change);
                change.handlerDataChange(data, requestObservableBean);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Iterator<OnPageDataChange<ListItem>> iterator2 = mOnPageDataChanges.iterator();
        while (iterator2.hasNext()) {
            try {
                OnPageDataChange<ListItem> change = iterator2.next();
                Log.d(TAG, "checkOnDataChange: change = " + change);
                change.handlerDataChange(data,-1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 加载数据, {@link PageLoader#refresh()} 和  {@link PageLoader#next()} 都将会触发此方法
     *
     * @param page 是一次行为的page ，不一定是当前页码。比如refresh那么 page = {@link #thisStartPage()}
     *             当加载下一页的时候，这个page先 +1，触发load然后传递过来此
     */
    private void load(int page) {

        UiUtilKt.getHANDLER_UI().post(() -> {
            if (page == thisStartPage()) {
                setStateInfo(new StateInfo(page, pageSize(), IState2.STATE_START_REFRASH));
            } else {
                setStateInfo(new StateInfo(page, pageSize(), IState2.STATE_START_NEXT));
            }
        });


        int dataSize = realData().size();
        /**
         * 回调函数来处理页码的逻辑，这里接口有默认实现{@link PageCallBack#convertPage(int, int, int, int)}
         */
        int p = pageCallBack.convertPage(page, dataSize, thisStartPage(), pageSize());
        Log.d(TAG, "load: page = " + p + " , dataSize = " + dataSize);

        //传递已经处理好的当前页码过去，获取回调函数的被观察者
        Observable<DataEntity> observable = pageCallBack.getRemoteData(p, pageSize());
        if (observable != null) {
            //保存此次的请求为对象
            RequestObservableBean<DataEntity> bean = new RequestObservableBean<>();
            //保存被观察者
            bean.setRequestObservable(observable);
            //保存此次请求的page
            bean.setPage(p);
            //保存此次pagesize
            bean.setPageSize(pageSize());
            /**
             * [RemoteObserver] 来订阅
             */
            observable.subscribe(RemoteObserver.instance(this, bean));
        } else {
            Log.d(TAG, "load: Observable<DataEntity> observable = callBack.getRemoteData(p) , Observable = null , not load ");
        }
    }

    /**
     * 加载下一页，数据
     * 核心是改变{@link #page}的value，基于{@link MutableLiveData}观察者触发 {@link #load(int)}
     */
    @Override
    public void next() {
        if (listener != null) {
            if (listener.onLoadMoreBefore()) {
                return;
            }
        }
        nextNewMethod(true);
    }

    private void nextNewMethod(boolean isEnableLimiteMoreLoadByMethodPara) {
        if (isEnableLimiteMoreLoadByMethodPara) {
            if (enableNextLoadLimit) {
                if (Math.abs(thisNextTime - System.currentTimeMillis()) <= thisNextTimeLimite) {
                    Log.d(TAG, "refresh: 加载下一页间隔时间短~");
                    return;
                }
            }
        }
        thisNextTime = System.currentTimeMillis();

        Log.d(TAG, "next: 2");
        if (isEndPage) {
            state.setValue(IPageEngine.NO_MORE_PAGE);
            setStateInfo(new StateInfo(-1, -1, IState2.NO_MORE_PAGE));
            return;
        }
        Log.d(TAG, "next: 3");
        if (page.getValue() == null) {
            refresh();
            return;
        }
        Log.d(TAG, "next: 4");
        state.setValue(IPageEngine.STATE_START_NEXT);
        Log.d(TAG, "next: 5");
        page.setValue(page.getValue() + 1);
    }

    /**
     * 重新加载
     * 其中分页第一项受{@link PageCallBack#thisStartPage()} 的回调来控制
     * 核心是改变{@link #page}的value，基于{@link MutableLiveData}观察者触发 {@link #load(int)}
     */
    @Override
    public void refresh() {
        if (listener != null) {
            if (listener.onRefreshBefore()) {
                return;
            }
        }
        refreshNewMethod(true);
    }

    private void refreshNewMethod(boolean isEnableLimiteRefreshByMethodPara) {
        if (isEnableLimiteRefreshByMethodPara) {
            if (enableRefreshLimit) {
                if (Math.abs(thisRefreshTime - System.currentTimeMillis()) <= thisRefreshTimeLimite) {
                    Log.d(TAG, "refresh: 刷新间隔时间短~");
                    return;
                }
            }
        }
        thisRefreshTime = System.currentTimeMillis();

        isEndPage = false;
        lastSize = 0;
        clearRemoteDatas();
        state.setValue(IPageEngine.STATE_START_REFRASH);
        page.setValue(pageCallBack.thisStartPage());
    }

    @Override
    public int state() {
        Integer o = state.getValue();
        if (o == null) {
            return NONE;
        }
        return o;
    }

    /**
     * 获取当前存储的分页数据
     *
     * @return
     */
    @NonNull
    @Override
    public List<ListItem> realData() {
        return data;
    }

    @Override
    public int loadState() {
        return state.getValue();
    }

    /**
     * 当前的页数
     *
     * @return
     */
    @Override
    public int page() {
        return maxPage;
    }

    @Override
    public int lastSize() {
        return lastSize;
    }


    private void postionModeBusiness(@NonNull RequestObservableBean<DataEntity> entity) {
        lastSize = data.size();
        List<ListItem> transList = pageCallBack.transformDataToList(entity.getBaseData());
//        state.setValue(CONVERT_POSTION);
        pageCallBack.convertListByNewData(data, transList);
        mRequestObservableLivedata.setValue(entity);
    }

    private void simpleModeBusiness(@NonNull RequestObservableBean<DataEntity> entity) {
        lastSize = data.size();
        if (entity.getPage() <= pageCallBack.thisStartPage()) {
            maxPage = thisStartPage();
            data.clear();
            state.setValue(IPageEngine.STATE_END_REFRASH);
            setStateInfo(new StateInfo(entity.getPage(), entity.getPageSize(), IState2.STATE_END_REFRASH));
        } else {
            maxPage++;
            state.setValue(IPageEngine.STATE_END_NEXT);
            setStateInfo(new StateInfo(entity.getPage(), entity.getPageSize(), IState2.STATE_END_NEXT));
        }

        Log.d(TAG, "onNext: lastSize = " + lastSize);
        List<ListItem> list = pageCallBack.transformDataToList(entity.getBaseData());
        if (checkEndDatas(list)) {
            isEndPage = true;
            state.setValue(IPageEngine.NO_MORE_BY_LOADED_SUCCED_PAGE);
            setStateInfo(new StateInfo(entity.getPage(), entity.getPageSize(), IState2.NO_MORE_BY_LOADED_SUCCED_PAGE));
            Log.d(TAG, "onNext: 已经没有数据了~ pagesize = " + pageCallBack.pageSize() + " , " + data.size());

        } else {
            isEndPage = checkEndDatas(list);
            state.setValue(IPageEngine.LOADED_SUCCED_PAGE);
            setStateInfo(new StateInfo(entity.getPage(), entity.getPageSize(), IState2.LOADED_SUCCED_PAGE));
            Log.d(TAG, "onNext: loader pagesize = " + pageCallBack.pageSize() + " , " + data.size());
            if (list.size() > 0) {
                loaderCount += list.size();
            }
        }
        if (list.size() > 0) {
            data.addAll(list);
        }
        Log.d(TAG, "onNext: datas.size = " + data.size());
        mRequestObservableLivedata.setValue(entity);
    }


    private boolean checkEndDatas(@Nullable List<?> list) {
        if (maxCount != -1) {
            return maxCount <= loaderCount;
        } else {
            if (list == null) {
                Log.d(TAG, "checkEndDatas: need loader list = null " + 0);
//                return checkEndDatasFunction != null && checkEndDatasFunction.checkDataIsNull(null);
            } else {
                Log.d(TAG, "checkEndDatas: need loader " + list.size());
            }
            return checkEndDatasFunction != null ? checkEndDatasFunction.checkDataIsNull(list) : (list == null || list.size() < pageCallBack.pageSize());
        }
    }

    @Override
    public int pageSize() {
        return pageCallBack.pageSize();
    }

    @Override
    public int thisStartPage() {
        return pageCallBack.thisStartPage();
    }

    @Override
    public void registerPageIdObserver(@NotNull androidx.lifecycle.Observer<Integer> observer) {
        if (pageCallBack.getThisLifecycle() != null) {
            loadId.observe(pageCallBack.getThisLifecycle(), observer);
        } else {
            loadId.observeForever(observer);
        }
    }

    @Override
    public void refreshNoLimite() {
        refreshNewMethod(false);
    }

    @Override
    public void nextNoLimite() {
        nextNewMethod(false);
    }

    @Override
    public void enableRefreshLimit(boolean enable) {
        enableRefreshLimit = enable;
    }

    @Override
    public void enableMoreLoadLimit(boolean enable) {
        enableNextLoadLimit = enable;
    }

    @Override
    public void clearNow() {
        thisRefreshTime = System.currentTimeMillis();
        isEndPage = false;
        lastSize = 0;
        state.setValue(IPageEngine.STATE_CLEAR);
        setStateInfo(new StateInfo(-1, -1, IState2.STATE_CLEAR));
        data.clear();
        clearRemoteDatas();
    }

    @Override
    public ListItem get(int position) {
        return data.get(position);
    }

    @Override
    public void setCheckDataEnd(@NonNull ICheckDataEnd check) {
        this.checkEndDatasFunction = check;
    }

    @Override
    public int getBusinessMode() {
        return pageCallBack.getBusinessMode();
    }


    @Override
    public boolean addOnPageDataChange(@NonNull OnPageDataChange2<ListItem, RequestObservableBean<DataEntity>> onDataChange) {
        return mOnPageDataChanges2.add(onDataChange);
    }

    @Override
    public boolean removeOnPageDataChange(@Nullable OnPageDataChange2<ListItem, RequestObservableBean<DataEntity>> onDataChange) {
        return mOnPageDataChanges2.remove(onDataChange);
    }

    @Override
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public void observerState(@NonNull LifecycleOwner lifecycleOwner, @NonNull androidx.lifecycle.Observer<Integer> observer) {
        state.observe(lifecycleOwner, observer);
    }

    @Override
    public void observerStateInfo(@NonNull LifecycleOwner lifecycleOwner, @NonNull androidx.lifecycle.Observer<StateInfo> observer) {
        stateInfo.observe(lifecycleOwner, observer);
    }


    public void setListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public IHolderOnDataChangeListener<ListItem> getHolderDataChangeListeners() {
        return this;
    }

    public void addRemoteObserver(@NotNull RemoteObserver<DataEntity> remoteObserver) {
        mRemoteObservers.add(remoteObserver);
    }

    private void clearRemoteDatas() {
        List<RemoteObserver<DataEntity>> remoteObserverList = new ArrayList<>(mRemoteObservers);
        mRemoteObservers.clear();
        if (remoteObserverList.size() > 0) {
            for (RemoteObserver<DataEntity> value : remoteObserverList) {
                value.clearNow();
            }
        }
    }

    @Override
    public void setStateInfo(StateInfo stateInfo) {
        this.stateInfo.setValue(stateInfo);
    }

    @Override
    public boolean addOnPageDataChange(@NonNull OnPageDataChange<ListItem> onDataChange) {
        return mOnPageDataChanges.add(onDataChange);
    }

    @Override
    public boolean removeOnPageDataChange(@Nullable OnPageDataChange<ListItem> onDataChange) {
        return mOnPageDataChanges.remove(onDataChange);
    }

    @Nullable
    @Override
    public IHolderOnDataChangeListener2<ListItem, RequestObservableBean<DataEntity>> getHolderDataChangeListeners2() {
        return this;
    }

    public interface Listener {

        boolean onRefreshBefore();

        boolean onLoadMoreBefore();


    }

}
