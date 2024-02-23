package kr.song.vttest.controller

import kr.song.vttest.utils.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ReadController {
  val logger = logger()

  @GetMapping("/test")
  suspend fun getTest(): String
  {
    logger.info("getTest")

    return "test"
  }
}