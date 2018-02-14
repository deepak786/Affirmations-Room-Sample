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

package com.affirmations.app.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

/**
 * Created by root on 24/1/18.
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private var loading = ObservableField<Boolean>(false)
    private var error = MutableLiveData<Int>()
    private var hideKeyboard = MutableLiveData<Boolean>()
    private var message = MutableLiveData<String>()
    private var user_id = ""

    protected fun setError(id: Int) {
        error.postValue(id)
    }

    fun getError(): MutableLiveData<Int> {
        return error
    }

    protected fun setHideKeyboard(hide: Boolean) {
        hideKeyboard.postValue(hide)
    }

    fun hideKeyboard(): MutableLiveData<Boolean> {
        return hideKeyboard
    }

    protected fun setLoading(value: Boolean) {
        loading.set(value)
    }

    fun getLoading(): ObservableField<Boolean> {
        return loading
    }

    protected fun setMessage(msg: String) {
        message.postValue(msg)
    }

    fun getMessage(): MutableLiveData<String> {
        return message
    }

    protected fun setUserId(user_id: String) {
        this.user_id = user_id
    }

    protected fun getUserId(): String {
        return user_id
    }
}