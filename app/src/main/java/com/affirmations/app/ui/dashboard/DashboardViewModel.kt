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

package com.affirmations.app.ui.dashboard

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.affirmations.app.base.BaseViewModel
import com.affirmations.app.database.Affirmations
import com.affirmations.app.database.AffirmationsDAO
import com.affirmations.app.database.AppDataBase
import com.affirmations.app.database.DataBaseHandler

/**
 * Created by root on 14/2/18.
 */
class DashboardViewModel(application: Application) : BaseViewModel(application) {
    private var appDatabase: AppDataBase = DataBaseHandler().getAppDatabase(getApplication())
    var randAffirmation = MutableLiveData<Affirmations>()
    var affirmationsDataBase: AffirmationsDAO = appDatabase.affirmationsDAO()

    init {
        if (affirmationsDataBase.getDefaultAffirmationsCount() == 0) {
            // add default affirmations
            val affirmation0 = Affirmations(affirmation = "I go beyond barriers to possibilities", isDefault = true)
            val affirmation1 = Affirmations(affirmation = "I don’t sweat the small stuff.", isDefault = true)
            val affirmation2 = Affirmations(affirmation = "I can. I will. End of story.", isDefault = true)
            val affirmation3 = Affirmations(affirmation = "I am adventurous. I overcome fears by following my dreams.", isDefault = true)
            val affirmation4 = Affirmations(affirmation = "I feed my spirit. I train my body. I focus my mind. It’s my time.", isDefault = true)
            val affirmation5 = Affirmations(affirmation = "I am in charge of how I feel and today I am choosing happiness.", isDefault = true)
            val affirmation6 = Affirmations(affirmation = "I am my own superhero.", isDefault = true)
            val affirmation7 = Affirmations(affirmation = "I will not compare myself to strangers on the Internet.", isDefault = true)
            val affirmation8 = Affirmations(affirmation = "I am choosing and not waiting to be chosen.", isDefault = true)
            val affirmation9 = Affirmations(affirmation = "I have the power to create change.", isDefault = true)
            affirmationsDataBase.insertAffirmation(affirmation0, affirmation1, affirmation2, affirmation3, affirmation4, affirmation5, affirmation6, affirmation7, affirmation8, affirmation9)
        }
    }

    fun getAllAffirmations(): LiveData<List<Affirmations>> {
        return affirmationsDataBase.getAllAffirmations()
    }

    fun getRandomAffirmation() {
        randAffirmation.postValue(affirmationsDataBase.getRandomAffirmation())
    }


}