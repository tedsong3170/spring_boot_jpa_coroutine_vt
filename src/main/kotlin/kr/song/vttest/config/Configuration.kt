package kr.song.vttest.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.coyote.ProtocolHandler
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.support.TaskExecutorAdapter
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.Executors

/**
 * HTTP 요청은 Virtual Thread를 통해 처리
 */
@Configuration
class TomcatConfiguration {
  @Bean
  fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*>? {
    return TomcatProtocolHandlerCustomizer<ProtocolHandler> { protocolHandler: ProtocolHandler ->
      protocolHandler.executor = Executors.newVirtualThreadPerTaskExecutor()
    }
  }
}

/**
 * 비동기 처리를 위한 설정
 */
@Configuration
@EnableAsync
class AsyncConfig {

  @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
  fun asyncTaskExecutor(): AsyncTaskExecutor {

    val taskExecutor = TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
    taskExecutor.setTaskDecorator(LoggingTaskDecorator())

    return taskExecutor
  }
}

class LoggingTaskDecorator : TaskDecorator {

  override fun decorate(task: Runnable): Runnable {

    val callerThreadContext = MDC.getCopyOfContextMap()

    return Runnable {
      callerThreadContext?.let {
        MDC.setContextMap(it)
      }
      task.run()
    }
  }
}

/**
 * Virtual Thread Coroutine Dispatcher
 */
val Dispatchers.VT : CoroutineDispatcher
  get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
