package com.cdsap.challenge.domain.mapper

interface Mapper<T, V> {
    fun transform(input: T): V
}