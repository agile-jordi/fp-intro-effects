package com.agilogy.fpintro.effects.generalized

import com.agilogy.fpintro.effects.{User, UserId}

import scala.language.higherKinds

object ExecutionModule {

  sealed trait Context[C[_]] {
    def thenIfOk[A, B](initial: C[A])(f: A => C[B]): C[B]
    def ok[A](value: A): C[A]
  }

}

class ProgramInGeneralizedContext {

  import ExecutionModule._
  import com.agilogy.fpintro.effects.base.AsyncModule._
  import com.agilogy.fpintro.effects.base.ResultModule._

  // We parametrize the repository with some "container" type C that will "contain" the result like Result or Async do
  trait UserRepository[C[_]] {
    def saveUser(u: User): C[Unit]
    def getUser(id: UserId): C[User]
  }

  // We parametrize the program with a container type holding the result as well
  // We need a ctx that allows us to generically sequence such programs (thenIfOk), and to cointainerize values (result)
  def program[C[_]](ctx: Context[C], repo: UserRepository[C], userId: UserId): C[Unit] = {
    val user     = repo.getUser(userId)
    val incdUser = ctx.thenIfOk(user)(u => ctx.ok(User(u.name, u.counter + 1)))
    ctx.thenIfOk(incdUser)(u => repo.saveUser(u))
  }

  // Now we can run the very same program as an async or as a result program:

  val asyncContext: Context[Async] = new Context[Async] {
    override def thenIfOk[A, B](initial: Async[A])(f: A => Async[B]): Async[B] = initial.andThen(f)
    override def ok[A](value: A): Async[A]                                 = async(value)
  }

  def asyncProgram(repo: UserRepository[Async], userId: UserId): Async[Unit] = program(asyncContext, repo, userId)

  val resultContext: Context[Result] = new Context[Result] {
    override def thenIfOk[A, B](initial: Result[A])(f: A => Result[B]): Result[B] = initial.ifOk(f)
    override def ok[A](value: A): Result[A]                                   = result(value)
  }

  def resultProgram(repo: UserRepository[Result], userId: UserId): Result[Unit] = program(resultContext, repo, userId)

}
