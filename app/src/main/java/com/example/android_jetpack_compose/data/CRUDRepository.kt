package com.example.android_jetpack_compose.data

abstract class CRUDRepository<T, IDType> {
    abstract fun create(item: T)
    abstract fun read(id: IDType): T?
    abstract fun update(id: IDType, newItem: T)
    abstract fun delete(id: IDType) : Boolean
}
