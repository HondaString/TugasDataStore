@file:Suppress("DEPRECATION")

package com.navigation.latihan.githubuserapp.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.navigation.latihan.githubuserapp.R

class PagerAdapter   (private val contentx : Context, fragment : FragmentManager, data: Bundle): FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var bundleFragment : Bundle = data

    @StringRes
    private val TAB = intArrayOf(R.string.nav1, R.string.nav2)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){

            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.bundleFragment
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return contentx.resources.getString(TAB[position])
    }

}
