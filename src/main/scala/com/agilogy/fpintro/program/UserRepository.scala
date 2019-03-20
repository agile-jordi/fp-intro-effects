package com.agilogy.fpintro.program

import com.agilogy.fpintro.{User, UserId}

import scala.language.higherKinds

trait UserRepository {

  def saveUser(u: User): AsyncOrResult[Unit]

  def getUser(id: UserId): AsyncOrResult[User]

}
