package com.example.doctor.recordList

class RecordRepository {

    fun fetchRecord() : List<RecordModel>{
        return listOf(
            RecordModel("https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ","2019 / 0 / 22", null),
                    RecordModel("https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ","2019 / 0 / 22", null)

        )
    }


}