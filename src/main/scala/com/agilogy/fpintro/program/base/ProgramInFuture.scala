package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

import scala.concurrent.Future

class ProgramInFuture {

  trait UserRepository {

    def saveUser(u: User): Future[Unit]

    def getUser(id: UserId): Future[User]

  }

  import scala.concurrent.ExecutionContext.Implicits.global

  def program(repo: UserRepository, userId: UserId): Future[Unit] =
    repo.getUser(userId).flatMap(u => Future(User(u.name, u.counter + 1))).flatMap(u => repo.saveUser(u))
}
