package com.example.doctor.introScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.doctor.R
import kotlin.collections.ArrayList

class IntroViewPageAdapter (private var mContext: Context, private var mListScreen : ArrayList<ScreenItem>) : PagerAdapter() {



    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.layout_screen, null)

        val imgSlide =  layoutScreen.findViewById<ImageView>(R.id.intro_img)
        val title = layoutScreen.findViewById<TextView>(R.id.intro_title)
        val description = layoutScreen.findViewById<TextView>(R.id.intro_description)

        title.text = mListScreen[position].Title
        description.text = mListScreen[position].Description
        imgSlide.setImageResource(mListScreen[position].ScreenImage)

        container.addView(layoutScreen)

        return layoutScreen
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mListScreen.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}