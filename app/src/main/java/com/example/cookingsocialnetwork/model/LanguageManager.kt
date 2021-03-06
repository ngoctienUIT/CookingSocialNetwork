package com.example.cookingsocialnetwork.model

import android.content.Context
import java.util.*

class LanguageManager(private val context: Context) {
    fun updateResource(language: String?) {
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}