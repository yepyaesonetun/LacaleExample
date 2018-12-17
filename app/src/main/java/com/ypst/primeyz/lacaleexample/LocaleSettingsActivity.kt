package com.ypst.primeyz.lacaleexample

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_locale_settings.*
import android.widget.Toast
import com.ypst.primeyz.lacaleexample.LocaleManager.Companion.LANGUAGE_ENGLISH
import com.ypst.primeyz.lacaleexample.LocaleManager.Companion.LANGUAGE_MYANMAR


class LocaleSettingsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent{
            return Intent(context, LocaleSettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locale_settings)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnEnglish.setOnClickListener { setNewLocale(LANGUAGE_ENGLISH, true) }

        btnMyanmar.setOnClickListener { setNewLocale(LANGUAGE_MYANMAR, true) }
    }

    private fun setNewLocale(language: String, restartProcess: Boolean): Boolean {
        LocaleExampleApp.localeManager!!.setNewLocale(this, language)

        val i = Intent(this, MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

        if (restartProcess) {
            System.exit(0)
        } else {
            Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
