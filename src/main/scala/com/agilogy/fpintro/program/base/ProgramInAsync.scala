package com.agilogy.fpintro.program.base

import com.agilogy.fpintro.effects.async.Async
import com.agilogy.fpintro.{User, UserId}

class ProgramInAsync {

  trait UserRepository {

    def saveUser(u: User): Async[Unit]

    def getUser(id: UserId): Async[User]

  }

  def program(repo: UserRepository, userId: UserId): Async[Unit] =
    repo.getUser(userId).andThen(u => Async.async(User(u.name, u.counter + 1))).andThen(u => repo.saveUser(u))

}
