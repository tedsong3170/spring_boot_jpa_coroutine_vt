package kr.song.vttest.utils

inline fun <reified T> T.logger() = org.slf4j.LoggerFactory.getLogger(T::class.java)!!