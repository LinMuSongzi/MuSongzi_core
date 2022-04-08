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
    
    
    
    
    
    
    
    
    
    
    
    



