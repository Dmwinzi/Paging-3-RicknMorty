package com.example.paging.Data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging.Data.Network.Characters
import com.example.paging.Data.Repository.Repository

class Characterspagingsource(private val repository: Repository) : PagingSource<Int,Characters>() {

    
    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
      return try {

          var currentpage = params.key ?: 1
          var response = repository.getallcharacters(currentpage)
          val data  = response.body()?.results  ?: emptyList()
          var responsedata  = mutableListOf<Characters>()
          responsedata.addAll(data)

          LoadResult.Page(
              data = responsedata,
              prevKey = if (currentpage == 1) null else -1,
              nextKey = currentpage.plus(1)
          )

      } catch (e : Exception){
          LoadResult.Error(e)
      }
    }
}