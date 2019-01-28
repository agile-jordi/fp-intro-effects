package com.agilogy.fpintro.effects.monad

import com.agilogy.fpintro.effects.{User, UserId}

import scala.language.higherKinds

object ExecutionModule {

  // Just Context but renaming stuff

  sealed trait Monad[F[_]] {
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
    def pure[A](value: A): F[A]
    // We need map in order to user `for` comprehensions (see below):
    def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
  }

  // Some Scala syntax sugar:

  object Monad{
    def apply[F[_]](implicit M: Monad[F]): Monad[F] = M
  }

  implicit class MonadOps[F[_]: Monad, A](v: F[A]){
    def flatMap[B](f: A => F[B]): F[B] = Monad[F].flatMap(v)(f)
    def map[B](f: A => B): F[B] = Monad[F].map(v)(f)
  }

  implicit class MonadPureOps[A](v: A){
    def pure[F[_]: Monad]: F[A] = Monad[F].pure(v)
  }

}

class ProgramInGeneralizedContext {

  import ExecutionModule._
  import com.agilogy.fpintro.effects.base.AsyncModule._
  import com.agilogy.fpintro.effects.base.ResultModule._

  // The very same repository

  trait UserRepository[F[_]] {
    def saveUser(u: User): F[Unit]
    def getUser(id: UserId): F[User]
  }

  // Same program with some syntax sugar:
  // `for` language construct replaces `flatMap`s and `map`s
  // `: Monad` means we need an implicit instance of Monad for the container type F

  def program[F[_]: Monad](repo: UserRepository[F], userId: UserId): F[Unit] = {
    for {
      user <- repo.getUser(userId)
      incdUser <- User(user.name, user.counter + 1).pure[F]
      _ <- repo.saveUser(incdUser)
    } yield ()

  }

  // The same contexts but with renames:

  implicit val asyncMonad: Monad[Async] = new Monad[Async] {
    override def flatMap[A, B](fa: Async[A])(f: A => Async[B]): Async[B] = fa.andThen(f)
    override def pure[A](value: A): Async[A]                                 = async(value)
  }

  val asyncProgram: (UserRepository[Async], UserId) => Async[Unit] = program[Async]

  implicit val resultMonad: Monad[Result] = new Monad[Result] {
    override def flatMap[A, B](fa: Result[A])(f: A => Result[B]): Result[B] = fa.ifOk(f)
    override def pure[A](value: A): Result[A]                                   = pure(value)
  }

  val resultProgram: (UserRepository[Result], UserId) => Result[Unit] = program[Result]




}
