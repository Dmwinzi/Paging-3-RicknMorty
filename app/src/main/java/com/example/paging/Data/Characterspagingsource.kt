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
            val nextpage = params.key ?: 1
            val character =  repository.getallcharacters(nextpage)

             LoadResult.Page(
                 data = character,
                 prevKey = if (nextpage == 1) null else nextpage -1,
                 nextKey = nextpage.plus(1)
             )

      } catch (e : Exception){
          LoadResult.Error(e)
      }
    }
}