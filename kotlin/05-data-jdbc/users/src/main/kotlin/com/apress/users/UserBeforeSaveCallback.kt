package com.apress.users

import org.springframework.data.relational.core.conversion.MutableAggregateChange
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback
import org.springframework.stereotype.Component
import java.util.List

@Component
class UserBeforeSaveCallback : BeforeSaveCallback<User> {
    override fun onBeforeSave(aggregate: User, aggregateChange: MutableAggregateChange<User>): User {
        aggregate.gravatarUrl = aggregate.gravatarUrl ?: UserGravatar.getGravatarUrlFromEmail(aggregate.email!!)
        aggregate.userRole = aggregate.userRole ?: mutableListOf(UserRole.INFO)
        return aggregate
    }
}
