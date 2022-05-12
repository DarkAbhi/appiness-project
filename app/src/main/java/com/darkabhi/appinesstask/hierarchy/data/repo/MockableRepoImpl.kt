package com.darkabhi.appinesstask.hierarchy.data.repo

import com.darkabhi.appinesstask.common.ResultWrapper
import com.darkabhi.appinesstask.data.network.helper.safeApiCall
import com.darkabhi.appinesstask.data.network.service.MockableApiService
import com.darkabhi.appinesstask.models.MockableResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MockableRepoImpl @Inject constructor(private val mockableApiService: MockableApiService) :
    MockableRepo {
    override suspend fun getMockableData(): ResultWrapper<MockableResponse> {
        return safeApiCall(Dispatchers.IO) {
            mockableApiService.getMockableData()
        }
    }
}