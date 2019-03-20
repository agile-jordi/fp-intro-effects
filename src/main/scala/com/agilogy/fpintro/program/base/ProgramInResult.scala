package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.effects.result.Result
import com.agilogy.fpintro.{User, UserId}

object ProgramInResult {

  trait UserRepository {

    def saveUser(u: User): Result[Unit]

    def getUser(id: UserId): Result[User]

  }

  def program(repo: UserRepository, userId: UserId): Result[Unit] =
    repo.getUser(userId).ifOk(u => Result.result(User(u.name, u.counter + 1))).ifOk(u => repo.saveUser(u))

}
