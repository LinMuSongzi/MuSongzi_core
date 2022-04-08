package com.musongzi.core.base.client.imp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.musongzi.core.base.client.ClientImpl
import com.musongzi.core.base.client.FragmentClient
import com.musongzi.core.base.client.FragmentControlClient

class FragmentBusinessControlClientImpl(f: FragmentClient) : ClientImpl<FragmentClient>(f),
    FragmentControlClient {


    private var hideMethod: (FragmentTransaction, Fragment, Boolean) -> FragmentTransaction =
        { it, f, isHide ->
            var t = it
            if (isHide) {
                t = t.hide(f)
            }
            t
        }


    override fun getFragmentByTag(tag: String) =
        base.getHolderFragmentManager().findFragmentByTag(tag)

    override fun addFragment(fragment: Fragment, tag: String?, isHide: Boolean) {
        base.getHolderFragmentManager().beginTransaction().add(layoutId(), fragment, tag).apply {
            hideMethod.invoke(this, fragment, isHide).commitAllowingStateLoss()

        }
    }

    override fun <F : Fragment> addFragment(
        fragmentClass: Class<F>,
        tag: String?,
        isHide: Boolean
    ) {
        addFragment(fragmentClass.newInstance(), tag, isHide)
    }

    override fun replaceFragment(fragment: Fragment, tag: String?, isHide: Boolean) {
        base.getHolderFragmentManager().beginTransaction().replace(layoutId(), fragment, tag)
            .apply {
                hideMethod.invoke(this, fragment, isHide).commitAllowingStateLoss()
            }
    }

    override fun <F : Fragment> replaceFragment(
        fragmentClass: Class<F>,
        tag: String?,
        isHide: Boolean
    ) {
        replaceFragment(fragmentClass.newInstance(), tag, isHide)
    }

    override fun removeFragment(tag: String) {
        base.getHolderFragmentManager().findFragmentByTag(tag)?.let {
            base.getHolderFragmentManager().beginTransaction().remove(it)
                .commitAllowingStateLoss()
        }
    }

    override fun removeFragment(fragment: Fragment) {
        base.getHolderFragmentManager().beginTransaction().remove(fragment)
            .commitAllowingStateLoss()
    }

    override fun <F : Fragment> removeFragment(fragmentClass: Class<F>) {
        val array = ArrayList<Fragment>()
        for (va in base.getHolderFragmentManager().fragments) {
            if (va.javaClass.name == fragmentClass.name) {
                array.add(va)
            }
        }
        if (array.isNotEmpty()) {
            var t = base.getHolderFragmentManager().beginTransaction();
            for (rF in array) {
                t = t.remove(rF)
            }
            t.commitAllowingStateLoss()
        }
    }

    override fun layoutId() = base.layoutId()

    override fun getHolderFragmentManager() = base.getHolderFragmentManager()

    override fun getHolderParentFragmentManager() = base.getHolderParentFragmentManager()


}