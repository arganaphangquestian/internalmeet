package com.rizkydian.internalmeet.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.utils.ADMINBOTTOMMENU
import com.rizkydian.remindmeet.utils.MEMBERBOTTOMMENU
import com.rizkydian.remindmeet.utils.SharedPrefs
import com.rizkydian.remindmeet.utils.USERROLE
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    private val mainAdapter = MainAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }
    private fun init() {
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        if(SharedPrefs.get(USERROLE, "") as String == "Admin") {
            sbb_main.setMenuRes(R.menu.bottom_admin_nav_menu, 0)
            mainAdapter.setData(ADMINBOTTOMMENU)
        } else {
            sbb_main.setMenuRes(R.menu.bottom_member_nav_menu, 0)
            mainAdapter.setData(MEMBERBOTTOMMENU)
        }
        vp_main.apply {
            adapter = mainAdapter
        }
        vp_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Do Something
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Do Something
            }

            override fun onPageSelected(position: Int) {
                sbb_main.setActiveItem(position)
            }
        })

        sbb_main.setActiveItem(0)
        sbb_main.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                vp_main.currentItem = pos
                return true
            }
        })
    }
}