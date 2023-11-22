package com.example.android_jetpack_compose.data

interface CRUDRepository<T, IDType> {
    suspend fun create(item: T): Result<T>
    suspend fun read(id: IDType): Result<T?>
    suspend fun update(id: IDType, newItem: T): Result<T>
    suspend fun delete(id: IDType): Result<T?>
}

interface ListRepository<T> {
    suspend fun getList(): Result<List<T>>
}
