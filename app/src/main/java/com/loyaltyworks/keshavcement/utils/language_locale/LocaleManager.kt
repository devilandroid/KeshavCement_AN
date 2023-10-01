package com.loyaltyworks.keshavcement.utils.language_locale

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.Locale

class LocaleManager {

    companion object{
        const val ENGLISH = "en"
        const val HINDI = "hi"
        const val KANNADA = "kn"


        /**
         * SharedPreferences Key
         */
        private val LANGUAGE_KEY = "language_key"


        /**
         * set current pref locale
         */
        fun setLocale(mContext: Context): Context? {
            return updateResources(mContext, getLanguagePref(mContext))
        }

        /**
         * Set new Locale with context
         */
        fun setNewLocale(mContext: Context, @LocaleDef language: String): Context? {
            setLanguagePref(mContext, language)
            return updateResources(mContext, language)
        }

        /**
         * Get saved Locale from SharedPreferences
         *
         * @param mContext current context
         * @return current locale key by default return english locale
         */
        fun getLanguagePref(mContext: Context?): String? {
            val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
            return mPreferences.getString(LANGUAGE_KEY, ENGLISH)
        }

        /**
         * set pref key
         */
        private fun setLanguagePref(mContext: Context, localeKey: String) {
            val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
            mPreferences.edit().putString(LANGUAGE_KEY, localeKey).apply()
        }

        /**
         * update resource
         */

        private fun updateResources(context: Context, language: String?): Context? {
            var context = context
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            if (Build.VERSION.SDK_INT >= 24) {
                config.setLocale(locale)
                context = context.createConfigurationContext(config)
            } else {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
            return context
        }

        /**
         * get current locale
         */

        fun getLocale(res: Resources): Locale? {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
        }

    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef(ENGLISH, HINDI, KANNADA)
    annotation class LocaleDef {
        companion object {
            var SUPPORTED_LOCALES = arrayOf<String>("ENGLISH", "HINDI", "KANNADA")
        }
    }



}