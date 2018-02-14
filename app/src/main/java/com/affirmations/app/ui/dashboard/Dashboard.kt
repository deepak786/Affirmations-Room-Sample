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


import android.arch.lifecycle.Observer
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.affirmations.app.R
import com.affirmations.app.base.BaseFragment
import com.affirmations.app.database.Affirmations
import com.affirmations.app.databinding.FragmentDashboardBinding


/**
 * A simple [Fragment] subclass.
 */
class Dashboard : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    private var anim: AnimationDrawable? = null
    private var adapter: DashboardAdapter? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize the adapter
        adapter = DashboardAdapter(null)
        binding.affirmationsList.layoutManager = LinearLayoutManager(context)
        binding.affirmationsList.setHasFixedSize(true)
        binding.affirmationsList.adapter = adapter

        // random affirmation
        viewModel.randAffirmation.observe(this, Observer {
            binding.randomAffirmation.text = it?.affirmation
        })
        viewModel.getRandomAffirmation()

        // get all affirmations
        viewModel.getAllAffirmations().observe(this, Observer {
            adapter?.updateItems(it)
        })

        binding.createAffirmation.setOnClickListener({
            val affirmation = Affirmations(affirmation = "This is my dummy affirmation")
            viewModel.affirmationsDataBase.insertAffirmation(affirmation)
        })

        // initialize the animation
        anim = binding.randomAffirmation.getBackground() as AnimationDrawable
        anim?.setEnterFadeDuration(3000)
        anim?.setExitFadeDuration(2000)
    }

    override fun onResume() {
        super.onResume()
        // start the animation
        if (anim != null && !anim?.isRunning!!)
            anim?.start()
    }

    override fun onPause() {
        super.onPause()
        // stop the animation
        if (anim != null && anim?.isRunning!!)
            anim?.stop()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getViewModelClass(): Class<DashboardViewModel> {
        return DashboardViewModel::class.java
    }


}// Required empty public constructor
