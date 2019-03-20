package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

object Program {

  trait UserRepository {

    def saveUser(u: User): Unit

    def getUser(id: UserId): User

  }

  def program(repo: UserRepository, userId: UserId): Unit = {
    val user     = repo.getUser(userId)
    val incdUser = User(user.name, user.counter + 1)
    repo.saveUser(incdUser)
  }

}
