package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

object Program {

  trait UserRepository {

    def saveUser(u: User): Unit

    def getUser(id: UserId): User

  }

  def program(repo: UserRepository, userId: UserId): Unit = {
    val u0 = repo.getUser(userId)
    val u1 = User(u0.name, u0.counter + 1)
    repo.saveUser(u1)
  }

}
