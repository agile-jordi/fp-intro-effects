package com.agilogy.fpintro.program.monad

import com.agilogy.fpintro.effects.async.Async
import com.agilogy.fpintro.effects.monad.Monad
import com.agilogy.fpintro.program.UserRepository
import com.agilogy.fpintro.{User, UserId}

import scala.language.higherKinds

object ProgramInMonad {

  // Same program with some syntax sugar:
  // `for` language construct replaces `flatMap`s and `map`s
  // `: Monad` means we need an implicit instance of Monad for the container type F

  import Monad.Syntax._

  def program[F[_]: Monad](repo: UserRepository[F], userId: UserId): F[Unit] =
    for {
      user     <- repo.getUser(userId)
      incdUser <- User(user.name, user.counter + 1).pure[F]
      _        <- repo.saveUser(incdUser)
    } yield ()

}

object ProgramInMonadAsync {

  import ProgramInMonad._

  implicit val asyncMonad: Monad[Async] = new Monad[Async] {
    override def flatMap[A, B](fa: Async[A])(f: A => Async[B]): Async[B] = fa.andThen(f)
    override def pure[A](value: A): Async[A]                             = Async.async(value)
  }

  val asyncProgram: (UserRepository[Async], UserId) => Async[Unit] = program[Async]

}
