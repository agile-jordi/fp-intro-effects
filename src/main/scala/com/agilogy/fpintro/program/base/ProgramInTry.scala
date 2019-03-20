package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

import scala.util.Try

class ProgramInTry {

  trait UserRepository {

    def saveUser(u: User): Try[Unit]

    def getUser(id: UserId): Try[User]

  }

  def program(repo: UserRepository, userId: UserId): Try[Unit] =
    repo.getUser(userId).flatMap(u => Try(User(u.name, u.counter + 1))).flatMap(u => repo.saveUser(u))
}
