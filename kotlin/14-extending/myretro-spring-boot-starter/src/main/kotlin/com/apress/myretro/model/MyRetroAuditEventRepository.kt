package com.apress.myretro.model

import org.springframework.data.repository.CrudRepository

interface MyRetroAuditEventRepository : CrudRepository<MyRetroAuditEvent, Long>
