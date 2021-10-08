package com.example.keepnotes.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType = orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType = orderType)

    fun copy(orderType: OrderType) : NoteOrder{
        return when(this) {
            is Title -> Title(orderType = orderType)
            is Date -> Date(orderType = orderType)
        }
    }
}