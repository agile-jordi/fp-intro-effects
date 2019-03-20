package com.agilogy.fpintro.program.continue

import com.agilogy.fpintro.effects.async.Async
import com.agilogy.fpintro.effects.continue.Continue
import com.agilogy.fpintro.program.base.ProgramInAsync
import com.agilogy.fpintro.{User, UserId}

import scala.language.higherKinds

object ProgramInContinue {}

// Now we can run the very same program as an async or as a result program:

object ProgramInContinueAsync {

  def asyncProgram(repo: ProgramInAsync.UserRepository, userId: UserId): Async[Unit] =
    ProgramInContinue.program(???, repo, userId)

}
