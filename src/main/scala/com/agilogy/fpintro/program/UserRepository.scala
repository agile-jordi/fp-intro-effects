package com.agilogy.fpintro.program

import com.agilogy.fpintro.{User, UserId}

import scala.language.higherKinds

// We parametrize the repository with some "container" type C that will "contain" the result like Result or Async do

trait UserRepository[C[_]] {
  def saveUser(u: User): C[Unit]

  def getUser(id: UserId): C[User]
}
