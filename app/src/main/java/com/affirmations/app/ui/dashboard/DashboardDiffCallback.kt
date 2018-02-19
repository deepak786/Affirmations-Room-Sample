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

import android.support.v7.util.DiffUtil
import com.affirmations.app.database.Affirmations

/**
 * Created by root on 14/2/18.
 */
class DashboardDiffCallback(private val oldList: List<Affirmations>?, private val newList: List<Affirmations>?) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData: Affirmations? = oldList?.get(oldItemPosition)
        val newData: Affirmations? = newList?.get(newItemPosition)

        return (oldData?.affirmation == newData?.affirmation
                && oldData?.id == newData?.id
                && oldData?.time == newData?.time)
    }
}