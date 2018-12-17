package com.ypst.primeyz.lacaleexample

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * Created by yepyaesonetun on 12/17/18.
 **/
class LocaleExampleApp : Application() {

    companion object {
        var localeManager: LocaleManager? = null

    }

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager!!.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager!!.setLocale(this)
    }
}