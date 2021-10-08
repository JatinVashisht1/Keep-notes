package com.example.keepnotes.domain.util

sealed class OrderType{
    object Ascending : OrderType()
    object Descending : OrderType()
}
