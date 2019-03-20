package com.agilogy.fpintro.effects.monad

import scala.language.higherKinds

trait Monad[F[_]] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def pure[A](value: A): F[A]

  // We need map in order to user `for` comprehensions (see below):
  def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
}

// Some Scala syntax sugar:

object Monad {

  def apply[F[_]](implicit M: Monad[F]): Monad[F] = M

  object Syntax {

    implicit class MonadOps[F[_]: Monad, A](v: F[A]) {
      def flatMap[B](f: A => F[B]): F[B] = Monad[F].flatMap(v)(f)

      def map[B](f: A => B): F[B] = Monad[F].map(v)(f)
    }

    implicit class MonadPureOps[A](v: A) {
      def pure[F[_]: Monad]: F[A] = Monad[F].pure(v)
    }

  }

}
