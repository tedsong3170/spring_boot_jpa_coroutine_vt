package kr.song.vttest.controller

import kr.song.vttest.models.User
import kr.song.vttest.utils.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ReadController {
  val logger = logger()

  @GetMapping("/test")
  suspend fun getTest(): User
  {
    logger.info("getTest")

    val user = User(
      1,
      "song",
      ""
    )

    return user
  }
}