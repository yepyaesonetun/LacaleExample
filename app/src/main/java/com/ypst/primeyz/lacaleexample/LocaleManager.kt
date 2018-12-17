package com.ypst.primeyz.lacaleexample

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build.VERSION_CODES.N
import java.util.*

/**
 * Created by yepyaesonetun on 12/17/18.
 **/
class LocaleManager(context: Context) {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val language: String
        get() = prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH)

    fun setLocale(c: Context): Context {
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        if (Utility.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    companion object {

        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_MYANMAR = "my"
        private const val LANGUAGE_KEY = "language_key"

        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Utility.isAtLeastVersion(N)) config.locales.get(0) else config.locale
        }
    }
}