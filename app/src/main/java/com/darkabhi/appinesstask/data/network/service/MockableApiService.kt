package com.darkabhi.appinesstask.data.network.service

import com.darkabhi.appinesstask.models.MockableResponse
import retrofit2.http.GET

/**
 * Created by Abhishek AN <iamabhishekan@gmail.com> on 12/5/2022.
 */
interface MockableApiService {
    @GET("myHierarchy")
    suspend fun getMockableData(): MockableResponse
}