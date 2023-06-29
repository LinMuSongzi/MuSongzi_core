package com.musongzi.core.base.page;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.heart.core.base.page.ICataloguePage;
import com.heart.core.base.page.PageArrayList;
import com.musongzi.core.base.page2.StateInfo;
import com.musongzi.core.itf.ILifeObject;
import com.musongzi.core.itf.data.IHolderDataConvert;
import com.musongzi.core.itf.page.Book;
import com.musongzi.core.itf.page.IAdMessage;
import com.musongzi.core.itf.page.ICheckDataEnd;
import com.musongzi.core.itf.page.IHolderOnDataChangeListener;
import com.musongzi.core.itf.page.ILimitOnLoaderState;
import com.musongzi.core.itf.page.ILimitRead;
import com.musongzi.core.itf.page.IRead;
import com.musongzi.core.itf.page.OnDataChangeListenerOwner;
import com.musongzi.core.itf.page.OnPageDataChange;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * Book ，此类是分页加载具体实现类
 *
 * @param <ListItem>   每项数据的泛型{@link #data}
 * @param <DataEntity> remote 数据端加载进入来的未过滤的基本数据泛型 通过{@link CallBack#transformDataToList(DataEntity)} 转化成{@link #data}
 */
@Deprecated
public class PageSupport<ListItem, DataEntity> implements ICataloguePage<ListItem>, Observer<DataEntity>, ILimitRead, ILimitOnLoaderState, IHolderCheckDataEnd, IHolderOnDataChangeListener<ListItem>
, OnDataChangeListenerOwner<ListItem> {

    private final List<OnPageDataChange<ListItem>> mOnPageDataChanges = new LinkedList<>();
    @Nullable
    private Listener listener = null;

    private int maxCount = -1;
    private static final String TAG = "PageSupport";

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
    protected CallBack<ListItem, DataEntity> callBack;
    /**
     * 最后一次的数据长度
     */
    private int lastSize = 0;
    private int size2 = 0;
    private int maxPage = -1;
    public boolean isEndPage = false;
    private ICheckDataEnd checkEndDatasFunction;
    /**
     * 状态
     */
    public final MutableLiveData<Integer> state = new MutableLiveData<>();
    /**
     * 此size不是数据长度，是数据更新的次数
     */
    private final MutableLiveData<Integer> size = new MutableLiveData<>();
    /**
     * page 当前的页数。不一定第一页就是0或者1
     */
    private final MutableLiveData<Integer> page = new MutableLiveData<>();
    /**
     * 识别一次加载 load->next 完整id，但是未想好写进代码里
     */
    private final MutableLiveData<Integer> loadId = new MutableLiveData<>(0);

    /**
     * 包裹的一个静态代理
     */
    @Deprecated
    private Observer<DataEntity> mFlashObserver;
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

    PageSupport() throws Exception {
        throw new Exception("不允许初始化,请使用带参数的构造方法 PageSupport(CallBack<ListItem, DataEntity> callBack) ");
    }

    public PageSupport(CallBack<ListItem, DataEntity> callBack) {
//        this.data = data;
        this.callBack = callBack;
        LifecycleOwner lifecycleOwner = callBack.getThisLifecycle();
//        Log.i(TAG, "PageSupport: " + callBack.pageSize());
        if (lifecycleOwner == null) {
            page.observeForever(integer -> {
                Log.i(TAG, "PageSupport: observeForever  = " + integer);
                load();
            });
        } else {
            page.observe(lifecycleOwner, integer -> {
                Log.i(TAG, "PageSupport: observe  = " + integer);
                load();
            });
        }

        if (lifecycleOwner == null) {
            state.observeForever(callBack::handlerState);
        } else {
            state.observe(lifecycleOwner, callBack::handlerState);
        }

        androidx.lifecycle.Observer<Integer> observer = size -> {
            Log.i(TAG, "PageSupport: change " + this);
            checkOnDataChange(data, callBack.getCode());
            callBack.handlerDataChange(data, callBack.getCode());
            if (callBack.createPostEvent() != null) {
//                EventBus.getDefault().post(callBack.createPostEvent());
            }
        };
        if (lifecycleOwner == null) {
            size.observeForever(observer);
        } else {
            size.observe(lifecycleOwner, observer);
        }
        if (lifecycleOwner != null && callBack.getAdMessage() != null) {
            lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
                @Override
                public void onCreate(@NonNull LifecycleOwner owner) {
                    callBack.getAdMessage().load();
                    size.observe(owner, integer -> {
                        callBack.getAdMessage().onChanged(data);
                    });
                    owner.getLifecycle().removeObserver(this);
                }
            });
            state.observe(lifecycleOwner, mState -> callBack.getAdMessage().onDataStateChange(mState));
        }
    }

    private void checkOnDataChange(List<ListItem> data, int code) {


        Iterator<OnPageDataChange<ListItem>> iterator = mOnPageDataChanges.iterator();
        while (iterator.hasNext()) {
            try {

                OnPageDataChange<ListItem> change = iterator.next();
                Log.d(TAG, "checkOnDataChange: change = " + change);
                change.handlerDataChange(data, code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    /**
     * 加载数据
     * 基于{@link CallBack#getRemoteData(int)} 页数
     */
    private void load() {
        int p = page.getValue();
        Log.i(TAG, "load: page = " + p);
        if (mFlashObserver == null) {
            mFlashObserver = new Observer<DataEntity>() {

                @Override
                public void onComplete() {
//                    super.onComplete();
                    PageSupport.this.onComplete();
                }

                @Override
                public void onSubscribe(Disposable d) {
//                    super.onSubscribe(d);
                    PageSupport.this.onSubscribe(d);
                }

                @Override
                public void onError(Throwable e) {
//                    super.onError(e);
                    PageSupport.this.onError(e);
                }

                @Override
                public void onNext(DataEntity dataEntity) {
//                    super.onNext(dataEntity);
                    PageSupport.this.onNext(dataEntity);
                }
            };
        }
        Observable<DataEntity> observable = callBack.getRemoteData(p);
        if (observable != null) {
            observable.subscribe(mFlashObserver);
        } else {
            Log.i(TAG, "load: Observable<DataEntity> observable = callBack.getRemoteData(p) , Observable = null , not load ");
        }
    }

    /**
     * 加载下一页，数据
     * 核心是改变{@link #page}的value，基于{@link MutableLiveData}观察者触发 {@link #load()}
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
                    Log.i(TAG, "refresh: 加载下一页间隔时间短~");
                    return;
                }
            }
        }
        thisNextTime = System.currentTimeMillis();

        Log.i(TAG, "next: 2");
        if (isEndPage) {
            state.setValue(NO_MORE_PAGE);
            return;
        }
        Log.i(TAG, "next: 3");
        if (page.getValue() == null) {
            refresh();
            return;
        }
        Log.i(TAG, "next: 4");
        state.setValue(STATE_START_NEXT);
        Log.i(TAG, "next: 5");
        page.setValue(page.getValue() + 1);
    }

//    private long thisNextTimeLimite() {
//        return 1500;
//    }

    /**
     * 重新加载
     * 其中分页第一项受{@link CallBack#thisStartPage()} 的回调来控制
     * 核心是改变{@link #page}的value，基于{@link MutableLiveData}观察者触发 {@link #load()}
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
                    Log.i(TAG, "refresh: 刷新间隔时间短~");
                    return;
                }
            }
        }
        thisRefreshTime = System.currentTimeMillis();

        isEndPage = false;
        lastSize = 0;
        state.setValue(STATE_START_REFRASH);
        page.setValue(callBack.thisStartPage());
    }

//    private long thisRefreshTimeLimite() {
//        return 1500;
//    }

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
        return page.getValue();
    }

    @Override
    public int lastSize() {
        return lastSize;
    }

    /**
     * 基于{@link Observer} 观察者回调，当订阅时
     *
     * @param d
     */
    @Override
    public void onSubscribe(Disposable d) {
        if (callBack.getObserver() != null) {
            callBack.getObserver().onSubscribe(d);
        }
    }

    /**
     * 基于{@link Observer} 观察者回调，当数据回调时
     * 其中判断一些基本参数，是否时刷新，是否时分页
     * 然后调用{@link CallBack#transformDataToList(Object)} )来转化数据为集合的 {@link #data}泛型类型
     *
     * @param entity
     */
    @Override
    public void onNext(DataEntity entity) {
        int mode = callBack.getBusinessMode();
        Log.i(TAG, "onNext: getBusinessMode = " + mode);
        switch (mode) {

            case POSTION_MODE:
                postionModeBusiness(entity);
                break;
            default:
                simpleModeBusiness(entity);
        }
    }

    private void postionModeBusiness(DataEntity entity) {
        lastSize = data.size();
        List<ListItem> transList = callBack.transformDataToList(entity);
//        state.setValue(CONVERT_POSTION);
        callBack.convertListByNewData(data, transList);
        size.setValue(++size2);
    }

    private void simpleModeBusiness(DataEntity entity) {
        lastSize = data.size();
        if (page.getValue() != null && page.getValue() <= callBack.thisStartPage()) {
            maxPage = 0;
            data.clear();
            state.setValue(STATE_END_REFRASH);
        } else {
            maxPage++;
            state.setValue(STATE_END_NEXT);
        }

        Log.i(TAG, "onNext: lastSize = " + lastSize);
        List<ListItem> list = callBack.transformDataToList(entity);
        if (checkEndDatas(list)) {
            state.setValue(NO_MORE_BY_LOADED_SUCCED_PAGE);
            Log.i(TAG, "onNext: 已经没有数据了~ pagesize = " + callBack.pageSize() + " , " + data.size());
            isEndPage = true;
        } else {
            state.setValue(LOADED_SUCCED_PAGE);
            isEndPage = checkEndDatas(list);
            Log.i(TAG, "onNext: again loader pagesize = " + callBack.pageSize() + " , " + data.size());
            if (list != null && list.size() > 0) {
                loaderCount += list.size();
            }
        }
        if (list != null && list.size() > 0) {
            data.addAll(list);
        }
        Log.i(TAG, "onNext: datas.size = " + data.size());
        size.setValue(++size2);
        if (callBack.getObserver() != null) {
            callBack.getObserver().onNext(entity);
        }
    }

    private int loaderCount = 0;

    private boolean checkEndDatas(@Nullable List<?> list) {
        if (maxCount != -1) {
            return maxCount <= loaderCount;
        } else {
            if (list == null) {
                Log.i(TAG, "checkEndDatas: need loader list = null " + 0);
//                return checkEndDatasFunction != null && checkEndDatasFunction.checkDataIsNull(null);
            } else {
                Log.i(TAG, "checkEndDatas: need loader " + list.size());
            }
            return checkEndDatasFunction != null ? checkEndDatasFunction.checkDataIsNull(list) : (list == null || list.size() < callBack.pageSize());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: " + e.getMessage());
        Log.i(TAG, "onError: pageSupport . " + Arrays.toString(e.getStackTrace()));
//        isLoaderNow = false;
        state.setValue(STATE_END_ERROR);
        if (callBack.getObserver() != null) {
            callBack.getObserver().onError(e);
        }
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: ");
        if (callBack.getObserver() != null) {
            callBack.getObserver().onComplete();
        }
    }

    @Override
    public int pageSize() {
        return callBack.pageSize();
    }

    @Override
    public int thisStartPage() {
        return callBack.thisStartPage();
    }

    @Override
    public void registerPageIdObserver(@NotNull androidx.lifecycle.Observer<Integer> observer) {
        if (callBack.getThisLifecycle() != null) {
            loadId.observe(callBack.getThisLifecycle(), observer);
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
        state.setValue(STATE_CLEAR);
        data.clear();
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
        return callBack.getBusinessMode();
    }

    @NonNull
    public IHolderOnDataChangeListener<ListItem> getHolderDataChangeListeners() {
        return this;
    }

    @Override
    public boolean addOnPageDataChange(@NonNull OnPageDataChange<ListItem> onDataChange) {
        return mOnPageDataChanges.add(onDataChange);
    }

    @Override
    public boolean removeOnPageDataChange(@Nullable OnPageDataChange<ListItem> onDataChange) {
        return mOnPageDataChanges.remove(onDataChange);
    }

    @Override
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public void observerState(@NonNull LifecycleOwner lifecycleOwner, @NonNull androidx.lifecycle.Observer<Integer> observer) {
        state.observe(lifecycleOwner,observer);
    }

    @Override
    public void observerStateInfo(@NonNull LifecycleOwner lifecycleOwner, @NonNull androidx.lifecycle.Observer<StateInfo> observer) {

    }


    /**
     * 提供给外部环境的基本回调函数
     *
     * @param <ListItem>参数泛型定义与   {@link PageSupport} 一致
     * @param <DataEntity>参数泛型定义与 {@link PageSupport} 一致
     */
    public interface CallBack<ListItem, DataEntity> extends IHolderDataConvert<ListItem, DataEntity>, Book, ILifeObject, IPostionModeBusiness<ListItem> {


        @Override
        default <I> void convertListByNewData(@NonNull List<I> data, @Nullable List<I> transList) {
            if (transList != null) {
                data.addAll(transList);
            }
        }

        default int getBusinessMode() {
            return IRead.SIMPLE_MODE;
        }

        /**
         * 身份id
         *
         * @return
         */
        int getCode();

        /**
         * 某些地方还在使用，因为一开始没想好，暂时先标记为过时
         *
         * @return
         */
        @Deprecated
        @Nullable
        Object createPostEvent();

        /**
         * 此回调不是覆盖原来的observer的意思，仅仅作为第二者参与数据回来时函数回调
         *
         * @return
         */
        @Nullable
        Observer<DataEntity> getObserver();

        void handlerState(Integer integer);

        /**
         * 广告辅助接口，用来处理数据集中广告业务
         *
         * @return
         */
        @Nullable
        IAdMessage<ListItem> getAdMessage();

    }


    public void setListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        boolean onRefreshBefore();

        boolean onLoadMoreBefore();


    }

}
