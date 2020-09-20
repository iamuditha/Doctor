package com.example.doctor.introScreen

import com.example.doctor.R

class IntroScreenRepository {

    fun fetchTabLayoutData(): ArrayList<ScreenItem> {
        return arrayListOf(
            ScreenItem(
                "Fresh Food",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                R.drawable.img1
            ),
            ScreenItem(
                "Fast Delivery",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                R.drawable.img2
            ),
            ScreenItem(
                "Easy Payment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                R.drawable.img3
            )
        )
    }
}