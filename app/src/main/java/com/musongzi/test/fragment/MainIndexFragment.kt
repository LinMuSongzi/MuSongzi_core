package com.musongzi.test.fragment

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.client.IMainIndexClient
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.test.business.BottomBusiness
import com.musongzi.test.databinding.FragmentMainIndexBinding
import com.musongzi.test.vm.MainIndexViewModel

/*** created by linhui * on 2022/7/20
 *
 *
 * activity/fragment view
 *
 * viewmodel   data
 *
 * business  业务
 *
 *
 * */
class MainIndexFragment : MszFragment<MainIndexViewModel, FragmentMainIndexBinding>(),
    IMainIndexClient {

    override fun initView() {
        getViewModel().getHolderBusiness().buildDataBySize()
        (getRecycleView().itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

        "BottomData".liveSaveStateObserver<List<String>>(getViewModel()){
            getViewModel().getHolderBusiness().getNext(BottomBusiness::class.java)?.update(dataBinding.root)
        }
//        if()
        getViewModel().getHolderBusiness().getNext(BottomBusiness::class.java)?.change4Bottom()

//        getViewModel().getHolderBusiness().getNext(A::class.java)

    }
    override fun getHolderContext(): Activity {
        return requireActivity()
    }

    override fun initEvent() {
//        id_title = findViewById(R.id.id_title)
//        object : AsyncTask<String, Int, StringChooseBean>() {
//            override fun doInBackground(vararg params: String?): StringChooseBean {
//                var time = 10_000
//                while (time >= 0) {
//                    publishProgress(time)
//                    time -= 50
//                    Thread.sleep(50)
//                }
//                return StringChooseBean().apply {
//                    title = "完成"
//                    id_ = time.toString() + "_ok"
//                }
//            }
//
//            override fun onPostExecute(result: StringChooseBean?) {
//                Log.i("AsyncTask", "onPostExecute: $result")
//            }
//
//            override fun onProgressUpdate(vararg values: Int?) {
//                getViewModel().getSource().realData()[0].title = "v:"+values[0]
//                getRecycleView().adapter?.notifyItemChanged(0)
////                toast()
//                Log.i("AsyncTask", "onProgressUpdate: ${values[0]}")
//            }
//
//        }.execute("haha")
    }

    override fun initData() {

    }

    override fun getRecycleView(): RecyclerView = dataBinding.idBottomView

    override fun getViewpage2(): ViewPager2 = dataBinding.idViewpage
}
