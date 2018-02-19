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

package com.affirmations.app.ui.affirmation


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.affirmations.app.R
import com.affirmations.app.base.BaseFragment
import com.affirmations.app.database.Affirmations
import com.affirmations.app.databinding.FragmentAffirmationBinding
import com.affirmations.app.ui.dashboard.DashboardViewModel


/**
 * A simple [Fragment] subclass.
 */
class AffirmationView : BaseFragment<FragmentAffirmationBinding, DashboardViewModel>() {
    private var affirmation: Affirmations? = null
    private var isDeleted: Boolean = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateToolbar(binding.toolbar)
        viewModel.getSelected().observe(this, Observer {
            affirmation = it
            binding.affirmation.setText(affirmation?.affirmation)
            if (affirmation != null)
                binding.toolbar.inflateMenu(R.menu.affirmation_action)
        })
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    isDeleted = true
                    viewModel.delete(affirmation!!)
                    activity?.onBackPressed()
                }
            }
            return@setOnMenuItemClickListener false
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_affirmation
    }

    override fun getViewModelClass(): Class<DashboardViewModel> {
        return DashboardViewModel::class.java
    }

    override fun onBackPressed(): Boolean {
        if (!isDeleted) {
            val thought = binding.affirmation.text.toString()
            if (affirmation == null) {
                if (!thought.isEmpty()) // create a new affirmation
                    viewModel.add(Affirmations(affirmation = thought))
            } else {
                if (affirmation?.affirmation != thought) { // update the existing one
                    val updatedAffirmation = affirmation?.copy(affirmation = thought)
                    viewModel.update(updatedAffirmation!!)
                }
            }
        }
        return false
    }
}// Required empty public constructor
