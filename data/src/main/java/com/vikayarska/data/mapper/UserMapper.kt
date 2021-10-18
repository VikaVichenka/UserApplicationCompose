package com.vikayarska.data.mapper

import com.vikayarska.data.db.user.entity.DbUser
import com.vikayarska.data.model.AppUser
import com.vikayarska.data.network.model.NetworkUser
import com.vikayarska.domain.model.User
import dagger.Provides
import java.util.*
import javax.inject.Inject


fun mapDBUser(user: DbUser): User {
    return User(
        user.id,
        user.firstName,
        user.lastName,
        user.birth,
        user.intro,
        User.UserImage(user.imageId, user.imageUrl)
    )
}

fun mapDBUserToAppUser(user: DbUser): AppUser {
    return AppUser(
        user.id,
        user.firstName,
        user.lastName,
        user.intro,
        user.imageUrl
    )
}

fun mapUserToDBUser(user: User): DbUser {
    return DbUser(
        user.id,
        user.firstName,
        user.lastName,
        user.birth,
        user.intro,
        user.image.imageId,
        user.image.imageUrl
    )
}


fun mapDBUserToApplicationUser(user: DbUser): AppUser {
    return AppUser(
        user.id,
        user.firstName,
        user.lastName,
        user.intro,
        user.imageUrl
    )
}


fun mapNetworkUserToDBUser(user: NetworkUser): DbUser {
    return DbUser(
        firstName = user.fullName.orEmpty().substringBefore(" "),
        lastName = user.fullName.orEmpty().substringAfter(" "),
        birth = Calendar.getInstance().generateRandomDate(),
        intro = user.intro.orEmpty(),
        imageId = user.image?.id ?: "",
        imageUrl = user.image?.url ?: ""
    )
}


private fun Calendar.generateRandomDate(): Date {
    val previousYear = Calendar.getInstance().get(Calendar.YEAR) - 1
    this.set((1950..previousYear).random(), (1..12).random(), (1..28).random())

    return this.time
}


class UserMappersFacade constructor(
    val mapDbToUser: (DbUser) -> User,
    val mapNetworkToDbUser: (NetworkUser) -> DbUser,
    val mapUserToDBUser: (User) -> DbUser
)
