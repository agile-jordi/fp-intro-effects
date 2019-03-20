package com.agilogy.fpintro.effects.result

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

object Result {

  def result[A](value: A): Result[A] = Ok(value)

}
