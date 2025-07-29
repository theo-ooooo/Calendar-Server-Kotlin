package com.calendar.api.global.advice

import com.calendar.api.global.dto.ApiResponse
import com.calendar.api.global.dto.ErrorResponse
import com.calendar.domain.global.exception.ErrorException
import com.calendar.domain.global.exception.ErrorType
import com.calendar.domain.global.exception.logger
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

//@RestControllerAdvice(basePackages = ["com.calendar"])
@RestControllerAdvice
class ApiExceptionAdvice: ResponseEntityExceptionHandler() {
    companion object {
        private val log by logger()
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        val errorResponse = ErrorResponse.of(ex.javaClass.simpleName, ex.message ?: "No message available")
        val apiResponse = ApiResponse.fail(statusCode.value(), errorResponse)

//        return super.handleExceptionInternal(ex, apiResponse, headers, statusCode, request)
        return ResponseEntity.status(statusCode.value()).headers(headers).body(apiResponse)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        log.error("MethodArgumentNotValidException", ex)
        val errors = ex.bindingResult.allErrors.mapNotNull {
            it.defaultMessage
        }
        val errorMessage = if (errors.isNotEmpty()) errors.joinToString("; ") else "Validation failed"
        val errorResponse = ErrorResponse.of(ex.javaClass.simpleName, errorMessage)
        val apiResponse = ApiResponse.fail(status.value(), errorResponse)

        return ResponseEntity.status(status.value()).body(apiResponse)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("ConstraintViolationException: {}", e.message, e)
        val bindingErrors =
            e.constraintViolations.associate { violation ->
                val path = violation.propertyPath.toString().substringAfterLast(".", "unknown")
                path to violation.message
            }
        val errorResponse = ErrorResponse.of(e.javaClass.simpleName, bindingErrors.toString())
        val apiResponse = ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), errorResponse)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
    ): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("MethodArgumentTypeMismatchException : {}", e.message, e)
        val errorCode: ErrorType = ErrorType.METHOD_ARGUMENT_TYPE_MISMATCH
        val errorResponse = ErrorResponse.of(e.javaClass.simpleName, errorCode.message)
        val apiResponse = ApiResponse.fail(errorCode.status, errorResponse)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse)
    }

    override fun handleHttpRequestMethodNotSupported(
        e: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        log.error("1111HttpRequestMethodNotSupportedException : {}", e.message, e)
        val errorCode: ErrorType = ErrorType.METHOD_NOT_ALLOWED
        val errorResponse = ErrorResponse.of(e.javaClass.simpleName, errorCode.message)
        val apiResponse = ApiResponse.fail(errorCode.status, errorResponse)
        return ResponseEntity.status(errorCode.status).body(apiResponse)
    }

    @ExceptionHandler(ErrorException::class)
    fun handleCustomException(e: ErrorException): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("CustomException : {}", e.message, e)
        val errorCode: ErrorType = e.errorType
        val errorResponse = ErrorResponse.of(errorCode.name, errorCode.message)
        val apiResponse = ApiResponse.fail(errorCode.status, errorResponse)
        return ResponseEntity.status(errorCode.status).body(apiResponse)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("Internal Server Error : {}", e.message, e)
        val internalServerError: ErrorType = ErrorType.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse.of(e.javaClass.simpleName, internalServerError.message)
        val apiResponse = ApiResponse.fail(internalServerError.status, errorResponse)
        return ResponseEntity.status(internalServerError.status).body(apiResponse)
    }
}