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

package com.affirmations.app.utils

import android.databinding.BindingAdapter
import android.text.format.DateFormat
import android.widget.TextView
import java.util.*

/**
 * Created by root on 14/2/18.
 */
@BindingAdapter("dateTime")
fun getDateTime(textView: TextView, timeInMillis: Long) {
    val context = textView.context
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis

    textView.text = DateFormat.format("MMM dd,yyyy hh:mm", calendar).toString()
}