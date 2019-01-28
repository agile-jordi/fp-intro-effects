package com.agilogy.fpintro.effects.base

import com.agilogy.fpintro.effects.{User, UserId}

object AsyncModule {

  final case class Async[A](run: () => A) {
    def get: A                                 = run()
    def andThen[B](f: A => Async[B]): Async[B] = Async(() => f(run()).get)
  }
  def async[A](value: A): Async[A] = Async(() => value)

}

class ProgramInAsyncContext {

  import AsyncModule._

  trait UserRepository {
    def saveUser(u: User): Async[Unit]
    def getUser(id: UserId): Async[User]
  }

  def program(repo: UserRepository, userId: UserId): Async[Unit] = {
    val user     = repo.getUser(userId)
    val incdUser = user.andThen(u => async(User(u.name, u.counter + 1)))
    incdUser.andThen(u => repo.saveUser(u))
  }

}
