package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.effects.result.Result
import com.agilogy.fpintro.{User, UserId}

object ProgramInResult {

  trait UserRepository {

    def saveUser(u: User): Result[Unit]

    def getUser(id: UserId): Result[User]

  }

  def program(repo: UserRepository, userId: UserId): Result[Unit] = {
    val u0 = repo.getUser(userId)
    val u1 = User(u0.name, u0.counter + 1)
    repo.saveUser(u1)
  }

}
