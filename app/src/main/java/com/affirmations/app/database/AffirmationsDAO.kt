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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*

/**
 * Created by root on 14/2/18.
 */
@Dao
interface AffirmationsDAO {

    @Query("SELECT * FROM Affirmations WHERE is_default == 0 ORDER BY created_at DESC")
    fun getAllAffirmations(): LiveData<List<Affirmations>>

    @Query("SELECT * FROM Affirmations WHERE is_default == 1 ORDER BY RANDOM() LIMIT 1;")
    fun getRandomAffirmation(): Affirmations

    @Insert
    fun insertAffirmation(vararg affirmation: Affirmations)

    @Update
    fun updateAffirmation(affirmation: Affirmations)

    @Query("SELECT count(*) FROM Affirmations WHERE is_default == 1")
    fun getDefaultAffirmationsCount(): Int

    @Delete
    fun deleteAffirmation(affirmation: Affirmations)

}