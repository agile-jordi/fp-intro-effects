package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.effects.async.Async
import com.agilogy.fpintro.{User, UserId}

object ProgramInAsync {

  trait UserRepository {

    def saveUser(u: User): Async[Unit]

    def getUser(id: UserId): Async[User]

  }

  def program(repo: UserRepository, userId: UserId): Async[Unit] = {
    val u0 = repo.getUser(userId)
    val u1 = User(u0.name, u0.counter + 1)
    repo.saveUser(u1)
  }

}
