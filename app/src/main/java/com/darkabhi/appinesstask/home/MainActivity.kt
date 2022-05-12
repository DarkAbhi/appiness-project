package com.darkabhi.appinesstask.home

import android.content.Intent
import android.os.Bundle
import com.darkabhi.appinesstask.base.BaseActivity
import com.darkabhi.appinesstask.databinding.ActivityMainBinding
import com.darkabhi.appinesstask.hierarchy.HierarchyActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.materialButton.setOnClickListener {
            startActivity(
                Intent(
                    this, HierarchyActivity::class.java
                )
            )
        }
    }
}