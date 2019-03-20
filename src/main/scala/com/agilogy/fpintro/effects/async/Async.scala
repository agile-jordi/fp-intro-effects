package com.agilogy.fpintro.effects.async

final case class Async[A](run: () => A) {
  def get: A                                 = run()
  def andThen[B](f: A => Async[B]): Async[B] = Async(() => f(run()).get)
}

object Async {
  def async[A](value: A): Async[A] = Async(() => value)
}
