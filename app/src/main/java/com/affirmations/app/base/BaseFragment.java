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

package com.affirmations.app.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.affirmations.app.BR;
import com.affirmations.app.BuildConfig;
import com.affirmations.app.R;

/**
 * Created by root on 25/1/18.
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    private DB binding;
    private VM viewModel;

    public abstract int getLayoutId();

    public abstract Class<VM> getViewModelClass();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
        binding.setVariable(BR.viewModel, viewModel);
//        viewModel.setUserId(Preferences.getInstance().getUserId(getContext()));

        viewModel.getError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer != -1)
                    showToast(integer);
            }
        });

        viewModel.hideKeyboard().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean)
                    hideKeyboard(BaseFragment.this);
            }
        });

        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (!TextUtils.isEmpty(s))
                    showToast(s);
            }
        });
        return binding.getRoot();
    }

    /**
     * get the Binding
     */
    protected DB getBinding() {
        return binding;
    }

    /**
     * get View Model
     */
    protected VM getViewModel() {
        return viewModel;
    }

    /**
     * inflate toolbar
     */
    protected void inflateToolbar(@Nullable Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null)
                        getActivity().onBackPressed();
                }
            });
        }
    }

    /**
     * show the message on console
     *
     * @param message message to display
     */
    protected void showOutput(String message) {
        if (BuildConfig.DEBUG)
            System.out.println(">>>>>" + message);
    }

    /**
     * function to display the @{@link Toast}
     *
     * @param message information to display on @{@link Toast}
     */
    protected void showToast(@NonNull String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int id) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(id), Toast.LENGTH_SHORT).show();
    }

    /**
     * get formatted String
     */
    protected String getFormattedString(String value) {
        if (TextUtils.isEmpty(value))
            return "";
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * open fragment
     */
    protected void openFragment(@NonNull final Fragment fragment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getActivity() instanceof BaseActivity)
                        ((BaseActivity) getActivity()).addFragment(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * hide the keyboard
     *
     * @param fragment @{@link Fragment}
     */
    protected void hideKeyboard(@NonNull Fragment fragment) {
        View view = fragment.getView();
        hideKeyboard(view);
    }

    /**
     * hide the keyboard
     *
     * @param view @{@link View}
     */
    protected void hideKeyboard(View view) {
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
