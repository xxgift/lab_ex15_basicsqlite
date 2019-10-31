package com.egco428.lab_ex15_basicsqlite

class Movie {
    var id: Long = 0
    var title: String = ""

    override fun toString(): String {
        return title.toString()
    }
}