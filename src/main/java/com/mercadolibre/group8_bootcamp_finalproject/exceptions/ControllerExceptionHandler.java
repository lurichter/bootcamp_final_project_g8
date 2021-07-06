package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

import com.newrelic.api.agent.NewRelic;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler({
			BadRequestException.class,
			PropertyReferenceException.class,
			org.springframework.dao.DuplicateKeyException.class,
			org.springframework.web.bind.support.WebExchangeBindException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			org.springframework.web.server.ServerWebInputException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiError badRequest(Exception ex) {
		LOGGER.info("executing exception handler (REST)");
		return new ApiError(
				ex.getClass().getName(),
				ex.getMessage(),
				HttpStatus.BAD_REQUEST.value()
		);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<ValidationError> handleException(MethodArgumentNotValidException ex) {
		LOGGER.info("Validation error");
		return ex.getBindingResult().getAllErrors()
				.stream()
				.map(this::mapError)
				.collect(Collectors.toList());
	}

	private ValidationError mapError(ObjectError objectError) {
		if (objectError instanceof FieldError) {
			return new ValidationError(((FieldError) objectError).getField(),
					objectError.getDefaultMessage());
		}
		return new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiError notFoundRequest(Exception ex) {
		return new ApiError(
				ex.getClass().getName(),
				ex.getMessage(),
				HttpStatus.NOT_FOUND.value()
		);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
		ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ApiError> handleApiException(ApiException e) {
		Integer statusCode = e.getStatusCode();
		boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
		NewRelic.noticeError(e, expected);
		if (expected) {
			LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
		} else {
			LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
		}

		ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
		LOGGER.error("Internal error", e);
		NewRelic.noticeError(e);

		ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

}