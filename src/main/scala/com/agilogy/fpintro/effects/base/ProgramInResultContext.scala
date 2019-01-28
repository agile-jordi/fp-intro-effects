package com.agilogy.fpintro.effects.base

import com.agilogy.fpintro.effects.{User, UserId}

object ResultModule {

  // Result[A] is either Ok[A] or Error

  sealed trait Result[+A] {
    def ifOk[B](f: A => Result[B]): Result[B]
  }
  final case class Ok[A](result: A) extends Result[A] {
    override def ifOk[B](f: A => Result[B]): Result[B] = f(result)
  }
  // Simplifying a lot, Nothing is the type of what won't ever have a value (like the correct result of this error)
  case object Error extends Result[Nothing] {
    override def ifOk[B](f: Nothing => Result[B]): Result[B] = Error
  }
  def result[A](value: A): Result[A] = Ok(value)

}

object ProgramInResultContext {

  import ResultModule._

  trait UserRepository {

    def saveUser(u: User): Result[Unit]
    def getUser(id: UserId): Result[User]
  }

  def program(repo: UserRepository, userId: UserId): Result[Unit] = {
    val user     = repo.getUser(userId)
    val incdUser = user.ifOk(u => result(User(u.name, u.counter + 1)))
    incdUser.ifOk(u => repo.saveUser(u))
  }

}
