package com.example.cookingsocialnetwork.model.data

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Time(
    var day: Long,
    var month: Long,
    var year: Long,
    var hour: Long,
    var minute: Long,
    var second: Long) {
    var dataTime = ""

    constructor() : this(1, 1, 1, 1, 1, 1)

    fun getTime(data: Map<String, Any>)
    {
        day = data["dayOfMonth"] as Long
        month = data["monthValue"] as Long
        year = data["year"] as Long
        hour = data["hour"] as Long
        minute = data["minute"] as Long
        second = data["second"] as Long
        dataTime = showTime()
    }

    private fun showTime(): String {
        val time = LocalDateTime.now()
        val myTime = LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt(), second.toInt())
        var timeLong = ChronoUnit.SECONDS.between(myTime, time)
        if (timeLong == 0.toLong()) return  "Vừa xong"
        if (timeLong in 1..59) return "$timeLong giây" //+ Resources.getSystem().getString(R.string.second)
        timeLong = ChronoUnit.MINUTES.between(myTime, time)
        if (timeLong in 1..59) return "$timeLong phút" //+ Resources.getSystem().getString(R.string.minute)
        timeLong = ChronoUnit.HOURS.between(myTime, time)
        if (timeLong in 1..23) return "$timeLong giờ" //+ Resources.getSystem().getString(R.string.hour)
        timeLong = ChronoUnit.DAYS.between(myTime, time)
        if (timeLong in 1..6) return "$timeLong ngày" //+ Resources.getSystem().getString(R.string.hour)
        else if (timeLong in 7..29) return "${timeLong / 7} tuần"
        return "$day/$month/$year"
    }
}