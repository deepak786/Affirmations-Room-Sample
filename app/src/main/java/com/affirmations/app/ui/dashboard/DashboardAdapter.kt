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

import android.os.Handler
import android.support.v7.util.DiffUtil
import com.affirmations.app.R
import com.affirmations.app.base.BaseRecyclerAdapter
import com.affirmations.app.database.Affirmations

/**
 * Created by root on 14/2/18.
 */
class DashboardAdapter(private var affirmations: List<Affirmations>?) : BaseRecyclerAdapter() {

    override fun getObjForPosition(position: Int): Any {
        return affirmations?.get(position)!!
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.list_item_affirmation
    }

    override fun setClickListeners(holder: Holder) {

    }

    override fun getItemCount(): Int {
        return affirmations?.size ?: 0
    }

    fun updateItems(affirmations: List<Affirmations>?) {
        val handler = Handler()
        Thread(Runnable {
            val diffResult = DiffUtil.calculateDiff(DashboardDiffCallback(this.affirmations, affirmations), false)
            handler.post { applyDiffResult(affirmations, diffResult) }
        }).start()
    }

    // This method is called when the background work is done
    private fun applyDiffResult(newItems: List<Affirmations>?, diffResult: DiffUtil.DiffResult) {
        dispatchUpdates(newItems, diffResult)
    }

    // This method does the work of actually updating
    // the backing data and notifying the adapter
    private fun dispatchUpdates(newItems: List<Affirmations>?, diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
        affirmations = newItems
    }
}