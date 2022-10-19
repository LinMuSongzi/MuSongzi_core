package com.musongzi.core.base.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.musongzi.core.ExtensionCoreMethod.dataBindingInflate

/**
 * 一个适配不同item type的适配器
 * @param ITEM 数据类型
 * @property cb HoldeGetItemType<ITEM>
 * @constructor
 */
class TypeSupportAdaper<ITEM> constructor(datas: List<ITEM>, private var typeBreakMethod: (Int) -> Int) : ListSetAdapter<ITEM>(datas) {

    constructor(datas: List<ITEM>) : this(datas, { ZERO })

    init {
        defualtBeanSet = false
    }

    /**
     * 所有viewtype的一个集合
     */
    private val typeDataBindings = SparseArray<HolderDataBindingClass<ITEM, *>>()

    /**
     * 默认数据绑定执行的方法，不关乎那种类型
     */
    private var allConvertMethod: ((ViewDataBinding, ITEM, Int) -> Unit)? = null

    /**
     * 默认数据界面创建的方法，不关乎那种类型
     */
    private var allViewCreateMethod: ((ViewDataBinding, Int, ViewGroup) -> Unit)? = null
    override fun getItemViewType(position: Int) = typeBreakMethod(position)

    /**
     * adapter 数据绑定回调
     * @param d ViewDataBinding
     * @param entity ITEM 数据
     * @param postion Int 位置
     */
    override fun convertData(d: ViewDataBinding, entity: ITEM, postion: Int) {
        typeDataBindings[getItemViewType(postion)].holderConvert?.convertData(d, entity, postion)
        allConvertMethod?.apply {
//            beanSet.invoke(d, entity!!)
            invoke(d, entity, postion)
        }
    }

    /**
     * adapter 数据界面创建回调
     * @param parent ViewGroup 当前的recycleView
     * @param viewType Int 类型
     * @return DataBindingViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val hc = typeDataBindings[viewType]
        val dataBinding: ViewDataBinding = dataBindingInflate(hc.hodlerDataBindingClass() as Class<ViewDataBinding>, parent) as ViewDataBinding
        hc.holderViewCreate?.convertData(dataBinding, viewType)
        allViewCreateMethod?.invoke(dataBinding, viewType, parent)
        return DataBindingViewHolder(dataBinding.root, dataBinding)
    }

    /**
     * 添加一个 [数据页面创建]时的回调基于databiding类型 默认使用 [ZORE]
     * @param clazz Class<D>
     * @param hc Function2<[@kotlin.ParameterName] D, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusViewCreate(clazz: Class<D>, hc: (dataBinding: D, type: Int) -> Unit) = plusViewCreate(ZERO, clazz, hc)

    /**
     * 添加一个 [数据页面创建]时的回调基于databiding类型 ,指定是哪个类型[ViewType]
     * @param type Int
     * @param clazz Class<D>
     * @param hc Function2<[@kotlin.ParameterName] D, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusViewCreate(type: Int, clazz: Class<D>, hc: (dataBinding: D, type: Int) -> Unit): TypeSupportAdaper<ITEM> {
        val hcDb = HolderDataBindingClass<ITEM, D>(type = type, clazz = clazz, null)
        hcDb.adaper = this
        hcDb.holderViewCreate = HolderViewCreate(hc)
        typeDataBindings.put(hcDb.t, hcDb)
        return this
    }

    /**
     * 添加一个 [数据绑定]时的回调基于databiding类型 默认使用 [ZORE]
     * @param clazz Class<D>
     * @param convert Function3<D, ITEM, Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusConvert(clazz: Class<D>, convert: (D, ITEM, Int) -> Unit) = plusConvert(ZERO, clazz, convert)

    /**
     * 添加一个 [数据绑定]时的回调基于databiding类型 默认使用 [ZORE]
     * @param clazz Class<D>
     * @param convert Function3<D, ITEM, Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     * @param isSetBeanFlag Boolean 是否默认设置setBean
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusConvert(clazz: Class<D>, convert: (D, ITEM, Int) -> Unit, isSetBeanFlag: Boolean? = true) = plusConvert(ZERO, clazz, convert, isSetBeanFlag)
    fun <D : ViewDataBinding> plusConvert(type: Int, clazz: Class<D>, convert: (D, ITEM, Int) -> Unit) = plusConvert(type, clazz, convert, true)

    /**
     * 添加一个 [数据绑定] 时 的回调基于databiding类型 ,指定是哪个类型[ViewType]
     * @param type Int 当前itemType类型
     * @param clazz Class<D>
     * @param convert Function3<D, ITEM, Int, Unit>
     * @param isSetBeanFlag Boolean 是否默认设置setBean
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusConvert(type: Int, clazz: Class<D>, convert: (D, ITEM, Int) -> Unit, isSetBeanFlag: Boolean? = true): TypeSupportAdaper<ITEM> {
        val isSetBeanFlag2 = isSetBeanFlag ?: true
        val hc = HolderDataBindingClass(type, clazz = clazz) {
            HolderConvert<ITEM, D>(isSetBeanFlag2) { d, i, p ->
                convert(d, i, p)
            }
        }
        hc.adaper = this
        typeDataBindings.put(hc.t, hc)
        return this
    }

    /**
     * 同时添加 [页面创建时] ,[绑定页面时]回调
     * @param clazz Class<D>
     * @param viewCreate Function2<[@kotlin.ParameterName] D, [@kotlin.ParameterName] Int, Unit>
     * @param convert Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] ITEM, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusAll(clazz: Class<D>, viewCreate: (dataBinding: D, type: Int) -> Unit, convert: (dataBinding: D, item: ITEM, postion: Int) -> Unit, isSetBeanFlag: Boolean? = true): TypeSupportAdaper<ITEM> = plusAll(ZERO, clazz, viewCreate, convert, isSetBeanFlag)
    fun <D : ViewDataBinding> plusAll(clazz: Class<D>, viewCreate: (dataBinding: D, type: Int) -> Unit, convert: (dataBinding: D, item: ITEM, postion: Int) -> Unit) = plusAll(ZERO, clazz, viewCreate, convert, true)
    fun <D : ViewDataBinding> plusAll(type: Int, clazz: Class<D>, viewCreate: (dataBinding: D, type: Int) -> Unit, convert: (dataBinding: D, item: ITEM, postion: Int) -> Unit) = plusAll(type, clazz, viewCreate, convert, true)

    /**
     * 同时添加 [页面创建时] ,[绑定页面时]回调。基于类型[ViewType]
     * @param type Int itemType 不同item的；类型
     * @param clazz Class<D>  dataBinding 的 class 用来反射获取实例
     * @param viewCreate Function2<[@kotlin.ParameterName] D, [@kotlin.ParameterName] Int, Unit>  相当于RecyclerView.Adapter.onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
     * @param convert Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] ITEM, [@kotlin.ParameterName] Int, Unit> 相当于RecyclerView.Adapter.onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
     * @return TypeSupportAdaper<ITEM>
     */
    fun <D : ViewDataBinding> plusAll(type: Int, clazz: Class<D>, viewCreate: (dataBinding: D, type: Int) -> Unit, convert: (dataBinding: D, item: ITEM, postion: Int) -> Unit, isSetBeanFlag: Boolean? = true): TypeSupportAdaper<ITEM> {
        val isSetBeanFlag2 = isSetBeanFlag ?: true
        val hc = HolderDataBindingClass<ITEM, D>(type = type, clazz = clazz) {
            HolderConvert(isSetBeanFlag2) { d, i, p ->
                convert(d, i, p)
            }
        }
        hc.holderViewCreate = HolderViewCreate { d, t ->
            viewCreate(d, t)
        }
        typeDataBindings.put(hc.t, hc)
        return this
    }

    /**
     * 添加一个[全局数据绑定]时的回调
     * @param convert Function3<[@kotlin.ParameterName] ViewDataBinding, [@kotlin.ParameterName] ITEM, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun joinConvert(convert: (dataBinding: ViewDataBinding, item: ITEM, postion: Int) -> Unit): TypeSupportAdaper<ITEM> {
        allConvertMethod = convert;
        return this
    }

    /**
     * 添加一个[全局数据页面创建]时的回调
     * @param convert Function3<[@kotlin.ParameterName] ViewDataBinding, [@kotlin.ParameterName] Int, ViewGroup, Unit>
     * @return TypeSupportAdaper<ITEM>
     */
    fun joinViewCreate(convert: (dataBinding: ViewDataBinding, type: Int, ViewGroup) -> Unit): TypeSupportAdaper<ITEM> {
        allViewCreateMethod = convert;
        return this
    }

    /**
     * 包裹着当前类型的databinding类型
     * @param ITEM
     * @property type Int
     * @property r Class<*>
     * @property holderConvert HolderConvert<ITEM, *>
     * @property holderViewCreate HolderViewCreate<*>?
     * @constructor
     */
    class HolderDataBindingClass<ITEM, D : ViewDataBinding>(type: Int, clazz: Class<D>, ct: (() -> HolderConvert<ITEM, D>)?) {
        lateinit var adaper: TypeSupportAdaper<ITEM>
        val t = type;
        val r = clazz
        val holderConvert: HolderConvert<ITEM, D>? = ct?.invoke()
        var holderViewCreate: HolderViewCreate<D>? = null;

        fun hodlerDataBindingClass() = r

//        constructor(clazz: Class<D>, ct: () -> HolderConvert<ITEM, D>) : this(ZERO, clazz, ct)
//
//        fun plusViewCreate(h: (dataBinding: D, type: Int) -> Unit): TypeSupportAdaper<ITEM> {
//            holderViewCreate = HolderViewCreate { d, t ->
//                h(d, t)
//            }
//            return adaper
//        }
    }

    /**
     * databinding创建时
     * @param D : ViewDataBinding
     * @property r Function2<D, [@kotlin.ParameterName] Int, Unit>
     * @constructor
     */
    class HolderViewCreate<D : ViewDataBinding>(run: (D, type: Int) -> Unit) {
        val r = run
        fun convertData(vdv: ViewDataBinding, type: Int) {
            r(vdv as D, type);
        }
    }

    /**
     * 数据装换
     * @param ITEM
     * @param D : ViewDataBinding
     * @property r Function3<D, ITEM, [@kotlin.ParameterName] Int, Unit>
     * @constructor
     */
    class HolderConvert<ITEM, D : ViewDataBinding>(isSetBeanFlag: Boolean, run: (D, ITEM, p: Int) -> Unit) {

        private var isSetBeanFlag = isSetBeanFlag;
        private val r = run

        fun convertData(d: ViewDataBinding, e: ITEM, position: Int) {
            if (isSetBeanFlag) {
                beanSet.invoke(d, e!!)
            }
            r(d as D, e, position);
        }
    }


//    operator fun <D : ViewDataBinding> TypeSupportAdaper<ITEM>.plus(run: (dataBinding: D, item: ITEM, postion: Int) -> Unit):TypeSupportAdaper<ITEM> {
//        plusConvert()
//    }


    companion object {
        const val ZERO = 0
        fun <ITEM, D : ViewDataBinding> build(datas: List<ITEM>, clazz: Class<D>, run: (dataBinding: D, item: ITEM, postion: Int) -> Unit) = TypeSupportAdaper(datas).plusConvert(clazz = clazz, run, true)
        fun <ITEM, D : ViewDataBinding> build(datas: List<ITEM>, clazz: Class<D>, run: (dataBinding: D, item: ITEM, postion: Int) -> Unit, isSetBeanFlag: Boolean? = true) = TypeSupportAdaper(datas).plusConvert(clazz = clazz, run, isSetBeanFlag)
        fun <ITEM, D : ViewDataBinding> build(datas: List<ITEM>, clazz: Class<D>, cRun: (D, Int) -> Unit, run: (dataBinding: D, item: ITEM, postion: Int) -> Unit, isSetBeanFlag: Boolean? = true) = TypeSupportAdaper(datas).plusAll(clazz = clazz, cRun, run, isSetBeanFlag)
        fun <ITEM, D : ViewDataBinding> build(type: Int, datas: List<ITEM>, clazz: Class<D>, cRun: (D, Int) -> Unit, run: (dataBinding: D, item: ITEM, postion: Int) -> Unit, isSetBeanFlag: Boolean? = true) = TypeSupportAdaper(datas).plusAll(type, clazz = clazz, cRun, run, isSetBeanFlag)
        fun <ITEM> build(datas: List<ITEM>, typeBreakMethod: (Int) -> Int) = TypeSupportAdaper(datas, typeBreakMethod)

    }


}