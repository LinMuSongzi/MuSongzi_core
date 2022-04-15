# MuSongzi_core
这是一个木松子的基本android 便捷搭建项目库。
使用到retrofit，okhttp
fragment+viewmodel+business方式构建项目


app项目中有对应的使用例子

1）比如  com.musongzi.test.engine.ArrayEngine 快速构建列表


@CollecttionsEngine(isEnableReFresh = true, isEnableLoadMore = true, isEnableEventBus = true)
class ArrayEngine : BaseMoreViewEngine<StringChooseBean, Array<StringChooseBean>>() {

    override fun getRemoteDataReal(page: Int): Observable<Array<StringChooseBean>> = getApi(Api::class.java).getArrayEngine(page)

    override fun myAdapter() = adapter(AdapterStringBinding::class.java)

    override fun transformDataToList(entity: Array<StringChooseBean>) = ArrayList<StringChooseBean>().let{
        it.addAll(entity)
        it
    }

}

object ExtensionMethod{
    ..........
      fun analysisCollectionsEngine(eClass: Class<*>): Fragment {
        val cAnnotation: CollecttionsEngine? = InjectionHelp.findAnnotation(eClass)
        val mCollectionsInfo = cAnnotation?.let {
            CollectionsViewModel.CollectionsInfo(it)
        } ?: CollectionsViewModel.CollectionsInfo()
        val bundle = Bundle();
        ModelFragment.composeProvider(bundle, false)
        mCollectionsInfo.engineName = eClass.name
        bundle.putParcelable(ViewListPageFactory.INFO_KEY, mCollectionsInfo)
        val collectionsFragment = CollectionsViewFragment();
        collectionsFragment.arguments = bundle
        return collectionsFragment
    }
    .........
  }
  
2)快速构建速配器（RecycleView)
  
  adapter(AdapterStringBinding::class.java)
  
  class ExtensionMethod{
  ................
  /**
     * 返回一个adapter，不做任何数据绑定
     * @receiver T
     * @param c Class<D>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>) = adapter(c, null);

    /**
     * 返回一个adapter,有一个onBindView
     * @receiver T
     * @param c Class<D>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>?
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        c: Class<D>,
        run: ((dataBinding: D, item: I, postion: Int) -> Unit)?
    ) = TypeSupportAdaper.build(realData(), c, run
        ?: { _, _, _ -> })
  .................
  }
    
3)使用Fragment(项目中有例子)
    
    TestMainFragment->
    class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient {
    ......
    }
    
    TestViewModel->
    class TestViewModel : BaseViewModel<ITestClient, TestBusiness>() {
    ......
    }
    
    ITestClient 回调函数（CallBack class use at View/activity/Fragment）->
    interface ITestClient:IClient {
    ......
    }
    
    TestBusiness->
    class TestBusiness : BaseLifeBusiness<TestViewModel>() {
    ......
    }
    
    TestMainFragment 默认持有一个泛型 TestViewModel
    TestViewModel 持有当前的IHodlerActivity(Fragment) 和 ITestClient(刷新ui回调，)  ，并且持有一个基于泛型的业务实例 TestBusiness；
                  他们的类注入在 IAttach.attachNow()此方法中注入到ViewModel(继承IAttach)：
   
                class MszViewModel<C:IClient,B:IBusiness>...{
                    ......
                    override fun attachNow(t: IHolderActivity?) {
                             if (isAttachNow()) {
                                    return
                             }
                            super.attachNow(t)
                            client = t?.getClient() as? C
                            business = createBusiness()
                            (business as? BaseLifeBusiness<IAgent>)?.setAgentModel(this)
                            business.afterHandlerBusiness()
                        }
    
                    ......
    
                    protected fun createBusiness(): B = InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()
    
                }
    
    
简单事务管理
    com.android.playmusic.l.viewmodel.imp.EventManger
    
    使用方式调用  {interface.class}.event.{method}即可
    
    任何注册接口和其父类方法都会被回调
    
    例如：
    --->
    class MyApplication : MultiDexApplication(){
    companion object {
        const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
    //注册了接口
        registerEvent(IClient::class.java){
            object:IClient{
                override fun showDialog(msg: String?) {
                    Log.i(TAG, "EventManger showDialog: $msg ${this@MyApplication}")
                }

                override fun disimissDialog() {

                }

                override fun disconnect() {

                }
            }
        }
    }
    
    
    -->
   class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient {

        override fun initData() {
        }

        override fun initEvent() {
            //使用接口回调
            ITestClient::class.java.event()?.showDialog("哈哈哈")
        }

        override fun showDialog(msg: String?) {
            super.showDialog(msg)
            Log.i(TAG, "EventManger showDialog: msg = $msg ")
        }


        override fun initView() {
            //注册了接口
            registerEvent(IClient::class.java) {
                this
            }

            Log.i(TAG, "initView: ${getMainViewModel()}")
            getMainViewModel()?.business?.checkBanner()
        }

        override fun showText(msg: String) {
            dataBinding.idMainContentTv.text = msg
        }
    }
    
    
    
    核心方法扩展方法
    ---->
    com.musongzi.core.base.manager.ActivityLifeManager
    
      fun <T> IHolderLifecycle.registerEvent(e: Class<T>, h: () -> T) {
            if (!e.isInterface) {
                Log.i("registerEvent", ": 1 ")
                return
            }
            Log.i("registerEvent", ": 2 ")
            getThisLifecycle()?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    Log.i("registerEvent", ": 3 ")
                    getEventManager().put(e, h)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    Log.i("registerEvent", ": 4 ")
                    getEventManager().remove(e, h.invoke())
                }

            })
        }

        fun <T> Class<T>.event(): T? {
            return if (!isInterface) {
                Log.i("eventFind", ": 1 必须是接口")
                null
            } else {
                Log.i("eventFind", ": 2 ")
                Proxy.newProxyInstance(classLoader, arrayOf(this)) { proxy, method, args ->
                    Log.i("eventFind", ": 2 ")
                    (getEventManager() as EventManger).asInvocationHandler().invoke(proxy, method, args)
                } as T
            }
        }
    
    
    
    
    
    
    
    



