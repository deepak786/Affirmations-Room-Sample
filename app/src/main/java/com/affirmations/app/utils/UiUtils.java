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

package com.affirmations.app.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Deepak Goyal on 27/12/16.
 * Author: Deepak Goyal
 */
public class UiUtils {

    // variable to hold the reference of @UiUtils class.
    private static UiUtils uiUtils;

    /**
     * private constructor
     */
    private UiUtils() {
    }

    /**
     * get the instance of @{@link UiUtils class}
     *
     * @return @uiUtils
     */
    public static UiUtils getInstance() {
        if (uiUtils == null)
            uiUtils = new UiUtils();
        return uiUtils;
    }


    /**
     * function to display the @{@link Toast}
     *
     * @param context context for the @{@link Toast}
     * @param message information to display on @{@link Toast}
     */
    public void showToast(@NonNull Context context, @NonNull String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * function to display the @{@link Toast}
     *
     * @param context  context for the @{@link Toast}
     * @param message  information to display on @{@link Toast}
     * @param duration @{@link Toast} duration
     */
    public void showToast(@NonNull Context context, @NonNull String message, int duration) {
        if (duration <= 0)
            showToast(context, message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * function to clear all the notifications
     *
     * @param context @{@link Context}
     */
    public void clearNotifications(@NonNull Context context) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to clear the notification of particular ID
     *
     * @param context @{@link Context}
     * @param id      notification id
     */
    public void clearNotifications(@NonNull Context context, int id) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * hide the keyboard
     *
     * @param activity @{@link android.support.v7.app.AppCompatActivity}
     */
    public void hideKeyboard(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        hideKeyboard(view);
    }

    /**
     * hide the keyboard
     *
     * @param fragment @{@link Fragment}
     */
    public void hideKeyboard(@NonNull Fragment fragment) {
        View view = fragment.getView();
        hideKeyboard(view);
    }

    /**
     * hide the keyboard
     *
     * @param view @{@link View}
     */
    public void hideKeyboard(View view) {
        if (view == null)
            return;

        IBinder token = view.getWindowToken();
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }
}
