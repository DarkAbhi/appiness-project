package com.darkabhi.appinesstask.hierarchy

import android.Manifest
import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.darkabhi.appinesstask.base.BaseActivity
import com.darkabhi.appinesstask.common.State
import com.darkabhi.appinesstask.databinding.ActivityHierarchyBinding
import com.darkabhi.appinesstask.hierarchy.adapter.MockableAdapter
import com.darkabhi.appinesstask.models.MockableResponse
import com.darkabhi.appinesstask.utils.PermissionUtils.checkPhonePermission
import com.darkabhi.appinesstask.utils.showLongToast
import com.darkabhi.appinesstask.utils.showShortSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class HierarchyActivity :
    BaseActivity<ActivityHierarchyBinding>(ActivityHierarchyBinding::inflate) {

    private val viewModel by viewModels<HierarchyViewModel>()
    private lateinit var mockableAdapter: MockableAdapter

    private val requestPhonePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                // User denied permission
                binding.root.showShortSnackBar("Please grant phone permission.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mockableAdapter = MockableAdapter({
            if (this.checkPhonePermission())
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${it.phoneNumber}")))
            else requestPhonePermission.launch(Manifest.permission.CALL_PHONE)
        }, {
            val smsUri = Uri.parse("smsto:${it.phoneNumber}")
            val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
            startActivity(smsIntent)
        })

        binding.hierarchyRv.adapter = mockableAdapter

        lifecycleScope.launch {
            launch {
                collectResponse()
            }
        }

        listenToSearch()
    }

    fun listenToSearch() {
        binding.searchEt.doAfterTextChanged {
            mockableAdapter.filter.filter(it.toString())
        }
    }

    private suspend fun collectResponse() {
        viewModel.mockableResponse.collect {
            binding.pb.isVisible = it is State.Loading
            when (it) {
                State.Empty -> {
                }
                is State.Failed -> {
                    Timber.e(it.message)
                }
                State.Loading -> {
                }
                is State.Success<*> -> {
                    it.data as MockableResponse
                    Timber.i(it.data.toString())
                    try {
                        mockableAdapter.setData(it.data.dataObject[0].hierarchyList[0].hierarchy.toMutableList())
                    } catch (e: Exception) {
                        this.showLongToast("An error occurred.")
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}