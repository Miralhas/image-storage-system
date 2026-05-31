package miralhas.github.imagestoragesystem.api.exception_handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import miralhas.github.imagestoragesystem.config.properties.StorageProperties;
import miralhas.github.imagestoragesystem.domain.exception.BusinessException;
import miralhas.github.imagestoragesystem.domain.exception.ResourceNotFoundException;
import miralhas.github.imagestoragesystem.infrastructure.exception.InternalServerError;
import miralhas.github.imagestoragesystem.shared.interfaces.MessageResolver;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;
	private final MessageResolver messageResolver;
	private final StorageProperties storageProperties;

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleUncaughtException(Exception ex, WebRequest webRequest) {
		log.error("Uncaught Exception:", ex);
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var detail = messageResolver.get("internalServerError");
		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setType(URI.create(getBaseUrl(webRequest)+"/errors/internal-server-error"));
		return problemDetail;
	}

	@ExceptionHandler(InternalServerError.class)
	public ProblemDetail handleInternalServerError(Exception ex, WebRequest webRequest) {
		log.error("Internal Server Error Exception:", ex);
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var detail = ex.getMessage();
		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setType(URI.create(getBaseUrl(webRequest)+"/errors/internal-server-error"));
		return problemDetail;
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ProblemDetail handlePropertyReferenceException(PropertyReferenceException ex, WebRequest webRequest) {
		var detail = ex.getMessage();
		var status = HttpStatus.BAD_REQUEST;
		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Pagination Bad Request");
		problemDetail.setType(URI.create(getBaseUrl(webRequest)+"/errors/pagination-bad-request"));
		return problemDetail;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setTitle("Resource Not Found");
		problemDetail.setType(URI.create(getBaseUrl(webRequest)+"/errors/resource-not-found"));
		return problemDetail;
	}

	@ExceptionHandler(BusinessException.class)
	public ProblemDetail handleBusinessException(BusinessException ex, WebRequest webRequest) {
		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
		problemDetail.setTitle("Invalid Request");
		problemDetail.setType(URI.create(getBaseUrl(webRequest)+"/errors/invalid-request"));
		return problemDetail;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		var errorsMap = new HashMap<String, String>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			errorsMap.put(error.getField(), message);
		});
		var detail = messageResolver.get("methodArgumentNotValid");
		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
		problemDetail.setTitle("Invalid Fields");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/invalid-fields"));
		problemDetail.setProperty("errors", errorsMap);
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(
			NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		String detail = messageResolver.get("noResourceFound", ex.getResourcePath());
		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Inexistent Resource");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/inexistent-resource"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		String detail = messageResolver.get("httpMethodNotSupported", ex.getMethod(), ex.getSupportedHttpMethods());
		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, detail);
		problemDetail.setTitle("HTTP Method not supported");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/http-method-not-supported"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		String detail = messageResolver.get("typeMismatch", ex.getPropertyName(),
				ex.getValue(), ex.getRequiredType().getSimpleName());

		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
		problemDetail.setTitle("Type Mismatch");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/type-mismatch"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
			MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		String detail = messageResolver.get("maximumUploadSize", storageProperties.multipartMaxFileSize());

		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.PAYLOAD_TOO_LARGE, detail);
		problemDetail.setTitle("Maximum Upload Size");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/maximum-upload-size-exceeded"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		if (ex.getCause() instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) ex.getCause(), headers, status, request);
		}
		var detail = messageResolver.get("httpMessageNotReadable");

		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Incomprehensible Message");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/incomprehensible-message"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(
			InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		String path = joinPath(ex.getPath());
		String detail = messageResolver.get("invalidFormat", path, ex.getValue(), ex.getTargetType().getSimpleName());

		var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle("Invalid Property Format");
		problemDetail.setType(URI.create(getBaseUrl(request)+"/errors/invalid-property-format"));
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	private String joinPath(List<JacksonException.Reference> path) {
		return path
				.stream()
				.map(JacksonException.Reference::getPropertyName)
				.filter(Objects::nonNull).collect(Collectors.joining("."));
	}

	private URI getBaseUrl(WebRequest webRequest) {
		var request = ((ServletWebRequest) webRequest).getRequest();
		var scheme = request.getScheme();
		var serverName = request.getServerName();
		var serverPort = request.getServerPort();
		return URI.create(scheme + "://" + serverName + ":" + serverPort);
	}

}