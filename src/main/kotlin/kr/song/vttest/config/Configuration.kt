package kr.song.vttest.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator
import java.util.concurrent.Executors

/**
 * HTTP 요청은 Virtual Thread를 통해 처리
 */
//@Configuration
//class TomcatConfiguration {
//  @Bean
//  fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*>? {
//    return TomcatProtocolHandlerCustomizer<ProtocolHandler> { protocolHandler: ProtocolHandler ->
//      protocolHandler.executor = Executors.newVirtualThreadPerTaskExecutor()
//    }
//  }
//}

/**
 * 비동기 처리를 위한 설정
 */
//@Configuration
//@EnableAsync
//class AsyncConfig {
//
//  @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//  fun asyncTaskExecutor(): AsyncTaskExecutor {
//
//    val taskExecutor = TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
//    taskExecutor.setTaskDecorator(LoggingTaskDecorator())
//
//    return taskExecutor
//  }
//}

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

//@Configuration
//class ContextConfig: WebMvcRegistrations {
//
//  override fun getRequestMappingHandlerAdapter(): RequestMappingHandlerAdapter {
//    return object: RequestMappingHandlerAdapter() {
//      override fun createInvocableHandlerMethod(handlerMethod: HandlerMethod): ServletInvocableHandlerMethod {
//        return object : ServletInvocableHandlerMethod(handlerMethod) {
//          override fun doInvoke(vararg args: Any?): Any? {
//            val method = bridgedMethod
//            ReflectionUtils.makeAccessible(method)
//            if (KotlinDetector.isSuspendingFunction(method)) {
//              // Exception handling skipped for brevity, copy it from super.doInvoke()
//              return CoroutinesUtils.invokeSuspendingFunction(method, bean, *args)
//            }
//            return super.doInvoke(*args)
//          }
//        }
//      }
//    }
//  }
//}