package com.example.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging.Data.Characterspagingsource
import com.example.paging.Data.Network.Characters
import com.example.paging.Data.Repository.Repository
import java.util.concurrent.Flow

class Mainviewmodel(val repository: Repository)  : ViewModel(){

    val character = Pager(PagingConfig(pageSize = 1)){
        Characterspagingsource(repository)
    }.flow.cachedIn(viewModelScope)

}