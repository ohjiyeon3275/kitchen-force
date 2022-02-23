package com.kitchenforce.domain.delivery

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RiderRepository: JpaRepository<Rider, Long>{
    override fun findById(id: Long): Optional<Rider>
}