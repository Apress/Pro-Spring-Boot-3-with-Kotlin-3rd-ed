package com.apress.users

interface Repository<D, ID> {
    fun save(domain: D): D
    fun findById(id: ID): D?
    fun findAll(): Iterable<D>
    fun deleteById(id: ID)
}
