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

  ExtensionMethod.analysisCollectionsEngine(ArrayEngine::class.java)
  //配置好信息构建一个Fragment
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
  



