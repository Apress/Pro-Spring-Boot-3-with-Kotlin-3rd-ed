package com.apress.myretro.persistence

import java.util.*

interface SimpleRepository<D, ID> {
    fun findById(id: ID): D?
    fun findAll(): Iterable<D>
    fun save(d: D): D
    fun deleteById(id: ID)
}
