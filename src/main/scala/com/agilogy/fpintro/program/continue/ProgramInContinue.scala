package com.agilogy.fpintro.program.continue

import com.agilogy.fpintro.effects.async.Async
import com.agilogy.fpintro.effects.continue.ContinueModule.Continue
import com.agilogy.fpintro.effects.result.Result
import com.agilogy.fpintro.program.UserRepository
import com.agilogy.fpintro.{User, UserId}

import scala.language.higherKinds

object ProgramInContinue {

  // We parametrize the program with a container type holding the result as well
  // We need a ctx that allows us to generically sequence such programs (thenIfOk), and to cointainerize values (result)
  def program[C[_]](ctx: Continue[C], repo: UserRepository[C], userId: UserId): C[Unit] = {
    val user     = repo.getUser(userId)
    val incdUser = ctx.continueWith(user)(u => ctx.value(User(u.name, u.counter + 1)))
    ctx.continueWith(incdUser)(u => repo.saveUser(u))
  }

}

// Now we can run the very same program as an async or as a result program:

object ProgramInContinueAsync {

  val asyncContext: Continue[Async] = new Continue[Async] {
    override def continueWith[A, B](initial: Async[A])(f: A => Async[B]): Async[B] = initial.andThen(f)

    override def value[A](value: A): Async[A] = Async.async(value)
  }

  def asyncProgram(repo: UserRepository[Async], userId: UserId): Async[Unit] =
    ProgramInContinue.program(asyncContext, repo, userId)

}

object ProgramInContinueResult {

  val resultContext: Continue[Result] = new Continue[Result] {
    override def continueWith[A, B](initial: Result[A])(f: A => Result[B]): Result[B] = initial.ifOk(f)

    override def value[A](value: A): Result[A] = Result.result(value)
  }

  def resultProgram(repo: UserRepository[Result], userId: UserId): Result[Unit] =
    ProgramInContinue.program(resultContext, repo, userId)

}
