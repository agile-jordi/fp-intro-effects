package com.agilogy.fpintro.effects.base

import com.agilogy.fpintro.effects.{User, UserId}

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
