package com.musongzi.core.base.business.collection

import com.musongzi.core.base.vm.IRefreshViewModel

object ViewListPageFactory {
    //    public static final String method = "createInstance";
    const val TITLE_KEY = "title"
    const val ENGINE_KEY = "VIEW_PAGE_ENGINE"
    const val ENABLE_REFRESH_KEY = "refresh"
    const val ENABLE_EMPTY_KEY = "empty_string"
    const val ENABLE_LOADMORE_KEY = "loadmore"
    const val ENGINE_MODEL_KEY = "viewmodel_key"
    const val ENGINE_EVENTBUS = "event_bus_key"
    const val ENGINE_EMPTY_LAYOUT = "empty_res"
    private const val TAG2 = "ViewListPageFactory"
    private const val TAG = "TypeSupportAdaper"

    @JvmStatic
    @Throws(Exception::class)
    fun create(name: String?, dictionary: IRefreshViewModel<*>): IHolderCollections {
        val viewPageEngine = dictionary.javaClass.classLoader!!.loadClass(name).newInstance() as IHolderCollections
        viewPageEngine.init(dictionary)
        return viewPageEngine
    }

//    fun fileBeanAdapter(datas: List<FileBean>) = TypeSupportAdaper(datas).plusConvert(clazz = AdapterFileBinding::class.java) { dataBinding, item, _ ->
//        val path = item.path;
//        dataBinding.root.setOnClickListener {
//            IntentHelper.shareSingleAudio(path)
//        }
//    }

//    fun myFansAdapter(datas: List<FansMineBean.ListBean>) = TypeSupportAdaper(datas).quick(clazz = AdapterFansBinding::class.java){ dataBinding, item, position->
//        dataBinding.idButton.click {
//            var type = 1;
//            if (item.isAttention2) {
//                type = 2;
//            }
//            getApiInstance().updateLike(Constant.getPhone(), Constant.getToken(), item.memberId, type)?.subObserver {
//                AttentionStatusObserver<AttentionStatusBean>(AttentionInfoChangeEvent(MyFansEngine.ACTION, type, item.memberId)) {
//                    item.attentionStatus = type
//                    callBack.notifyDataSetChangedItem(position)
//                }
//            }
//        }
//
//        dataBinding.head.click {
//            ActivityManager.startUserInformationActivity(item.memberId, null);
//        }
//    }


}