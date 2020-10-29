package com.example.doctor.introScreen

import com.example.doctor.R

class IntroScreenRepository {

    fun fetchTabLayoutData(): ArrayList<ScreenItem> {
        return arrayListOf(
            ScreenItem(
                "Easy Authentication",
                "Provide you a Digital Identity and Verify your Identity to the  Patient Using a Decentralized Identity Management Process",
                R.raw.authentication_animation
            ),
            ScreenItem(
                "Lots of records",
                "Access and View Patient's Full Medical History using ",
                R.raw.medical_history_animation
            ),
            ScreenItem(
                "Quick Sorting",
                "Sort and Filter Test Reports based on the Test Type and the Date of Uploaded",
                R.raw.sort_animation
            )
        )
    }
}