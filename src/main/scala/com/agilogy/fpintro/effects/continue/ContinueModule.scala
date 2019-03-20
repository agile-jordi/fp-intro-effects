package com.agilogy.fpintro.effects.continue

import scala.language.higherKinds

object ContinueModule {

  trait Continue[C[_]] {
    def continueWith[A, B](initial: C[A])(f: A => C[B]): C[B]

    def value[A](value: A): C[A]
  }

}
