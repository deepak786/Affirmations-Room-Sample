/*
 * Copyright (C) 2018 Deepak Goyal
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.affirmations.app.preferences

import android.content.Context
import android.content.SharedPreferences
import com.affirmations.app.R

/**
 * Created by root on 20/2/18.
 */
class Preferences private constructor() {
    init {
        println("This ($this) is a singleton")
    }

    private object Holder {
        val INSTANCE = Preferences()
    }

    companion object {
        val instance: Preferences by lazy { Holder.INSTANCE }

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(context.getString(R.string.pref_file_Name), Context.MODE_PRIVATE)
        }

        fun isSwipeThroughDisplayed(context: Context): Boolean {
            return getPreferences(context).getBoolean(context.getString(R.string.pref_swipe_through), false)
        }

        fun setSwipeThrough(context: Context) {
            getPreferences(context).edit().putBoolean(context.getString(R.string.pref_swipe_through), true).apply()
        }
    }
}