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

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.widget.Toast
import com.affirmations.app.BuildConfig
import com.affirmations.app.R
import com.affirmations.app.utils.UiUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by root on 24/1/18.
 */

open class BaseActivity : AppCompatActivity() {

    protected fun openActivity(@NonNull activity: BaseActivity, finishCurrent: Boolean = true) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        if (finishCurrent) finish()
    }

    // double back pressed function
    private var back_pressed: Long = 0

    /**
     * show the message on console
     *
     * @param message message to display
     */
    protected fun showOutput(message: String) {
        if (BuildConfig.DEBUG)
            println(">>>>>" + message)
    }

    /**
     * function to display the @[android.widget.Toast]
     *
     * @param message information to display on @[Toast]
     */
    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * function to add the fragment
     */
    fun addFragment(fragment: Fragment) {
        showFragment(fragment, fragment.javaClass.simpleName, true, false, 0, 0)
    }

    /**
     * function to show the fragment
     *
     * @param fragment fragment to be shown
     * @param isAdd    true-> add fragment, false->replace fragment
     */
    private fun showFragment(fragment: Fragment, tag: String, isAdd: Boolean, hasAnimation: Boolean, enterAnim: Int, exitAnim: Int) {
        UiUtils.getInstance().hideKeyboard(this)
        val fragmentManager = supportFragmentManager
        // check if the fragment is in back stack
        val fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0)
        if (!fragmentPopped) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (hasAnimation)
                fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
            if (isAdd)
                fragmentTransaction.add(R.id.fragment_container, fragment, tag)
            else
                fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.commit()
        }
    }

    /**
     * function to get the fragment
     */
    protected fun getFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.fragment_container)
    }

    /**
     * get Back Stack count
     */
    protected fun getBackStackCount(): Int {
        val fragmentManager = supportFragmentManager
        return fragmentManager.backStackEntryCount
    }

    /**
     * function to go back to previous fragment
     */
    protected fun oneStepBack() {
        UiUtils.getInstance().hideKeyboard(this)
        val fts = supportFragmentManager.beginTransaction()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount >= 2) {
            try {
                fragmentManager.popBackStackImmediate()
                fts.commit()
            } catch (e: Exception) {
                e.printStackTrace()
                /*fts.remove(getFragment());
                fts.commitNowAllowingStateLoss();*/
            }

        } else {
            doubleClickToExit()
        }
    }

    protected fun doubleClickToExit() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            finish()
        else
            showToast(getString(R.string.exitApp) + " " + getString(R.string.app_name))
        back_pressed = System.currentTimeMillis()
    }

    /**
     * Clearing the Fragment backStack
     */
    protected fun clearBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    /**
     * function to display all the extras of the intent
     *
     * @param extras bundle to extract
     */
    fun printIntentExtras(extras: Bundle?) {
        if (extras != null)
            for (key in extras.keySet()) {
                val value = extras.get(key)
                showOutput(String.format("%s %s (%s)", key, value?.toString(), value?.javaClass?.name))
            }
        else
            showOutput("Empty Bundle")
    }
}