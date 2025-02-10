package com.apress.myretro.persistence

interface Repository<D, ID> {
    fun save(domain: D): D?
    fun findById(id: ID): D?
    fun findAll(): MutableIterator<D>
    fun delete(id: ID)
}
