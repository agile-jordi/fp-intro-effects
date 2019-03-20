package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

import scala.util.Try

class ProgramInTry {

  trait UserRepository {

    def saveUser(u: User): Try[Unit]

    def getUser(id: UserId): Try[User]

  }

  def program(repo: UserRepository, userId: UserId): Try[Unit] = {
    val u0 = repo.getUser(userId)
    val u1 = User(u0.name, u0.counter + 1)
    repo.saveUser(u1)
  }

}
