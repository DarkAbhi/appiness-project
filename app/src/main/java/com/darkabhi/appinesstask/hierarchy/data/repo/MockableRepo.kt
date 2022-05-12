package com.darkabhi.appinesstask.hierarchy.data.repo

import com.darkabhi.appinesstask.common.ResultWrapper
import com.darkabhi.appinesstask.models.MockableResponse

interface MockableRepo {
    suspend fun getMockableData(
    ): ResultWrapper<MockableResponse>
}