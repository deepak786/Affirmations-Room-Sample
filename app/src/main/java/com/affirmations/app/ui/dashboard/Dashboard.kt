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
import com.affirmations.app.ui.affirmation.AffirmationView


/**
 * A simple [Fragment] subclass.
 */
class Dashboard : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(), DashboardAdapterItemClickListener {
    private var anim: AnimationDrawable? = null
    private var adapter: DashboardAdapter? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize the adapter
        adapter = DashboardAdapter(ArrayList<Affirmations>(), this)
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
            if (it != null) {
                if (it.isNotEmpty()) {
                    binding.affirmationsList.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                    adapter?.updateItems(it)
                    return@Observer
                }
            }
            binding.affirmationsList.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE

        })

        binding.createAffirmation.setOnClickListener({
            viewModel.select(null)
            openFragment(AffirmationView())
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

    override fun onDashboardItemClick(affirmation: Affirmations?) {
        viewModel.select(affirmation)
        openFragment(AffirmationView())
    }

    override fun onBackPressed(): Boolean {
        return false
    }


}// Required empty public constructor
