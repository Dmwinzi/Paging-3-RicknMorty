package com.example.paging.Data.Network

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)