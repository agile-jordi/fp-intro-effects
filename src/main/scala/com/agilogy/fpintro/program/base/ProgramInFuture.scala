package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.{User, UserId}

import scala.concurrent.Future

class ProgramInFuture {

  trait UserRepository {

    def saveUser(u: User): Future[Unit]

    def getUser(id: UserId): Future[User]

  }

  import scala.concurrent.ExecutionContext.Implicits.global

  def program(repo: UserRepository, userId: UserId): Future[Unit] = {
    val u0 = repo.getUser(userId)
    val u1 = User(u0.name, u0.counter + 1)
    repo.saveUser(u1)
  }
}
