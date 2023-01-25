package com.example.paging.Data.Repository

import com.example.paging.Data.Network.Characters
import com.example.paging.Data.Network.PageInfo
import com.example.paging.Data.Network.RicknMortApi
import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.Query

class Repository(private val ricknMortApi: RicknMortApi) : RicknMortApi{

    override suspend fun getallcharacters(@Query(value = "page") page: Int): Response<PageInfo> {
        delay(3000L)
        return ricknMortApi.getallcharacters(page)
    }

}