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

package com.affirmations.app.database

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by root on 14/2/18.
 */
class DataBaseHandler {
    private fun DataBaseHandler() {}

    private var INSTANCE: AppDataBase? = null
    fun getAppDatabase(context: Context): AppDataBase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDataBase::class.java, "Affirmations")
                    .allowMainThreadQueries()
                    .build()
        }
        return INSTANCE as AppDataBase
    }
}