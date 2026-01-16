package br.com.foodhub.infra.web.handler;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidCnpjException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidOpeningHoursException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import br.com.foodhub.core.domain.exceptions.user.InvalidCpfException;
import br.com.foodhub.core.domain.exceptions.user.InvalidEmailException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =======================
       404 — NOT FOUND
       ======================= */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        return build(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request,
                "resource-not-found"
        );
    }

    /* =======================
       409 — CONFLICT
       ======================= */

    @ExceptionHandler(ResourceConflictException.class)
    public ProblemDetail handleConflict(
            ResourceConflictException ex,
            HttpServletRequest request
    ) {
        return build(
                HttpStatus.CONFLICT,
                "Conflito de recurso",
                ex.getMessage(),
                request,
                "resource-conflict"
        );
    }

    /* =======================
       400 — BAD REQUEST
       ======================= */

    @ExceptionHandler({
            BusinessRuleViolationException.class,
            RequiredFieldException.class,
            InvalidCpfException.class,
            InvalidEmailException.class,
            InvalidCnpjException.class,
            InvalidOpeningHoursException.class,
            AddressNotBelongsToUserException.class
    })
    public ProblemDetail handleBadRequest(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return build(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                ex.getMessage(),
                request,
                "validation-error"
        );
    }

    /* =======================
       422 — BEAN VALIDATION
       ======================= */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String detail = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        return build(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Erro de validação",
                detail,
                request,
                "bean-validation-error"
        );
    }

    /* =======================
       500 — INTERNAL SERVER ERROR
       ======================= */

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno",
                "Erro inesperado. Contate o suporte.",
                request,
                "internal-server-error"
        );
    }

    /* =======================
       Helper
       ======================= */

    private ProblemDetail build(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request,
            String type
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setInstance(java.net.URI.create(request.getRequestURI()));
        problem.setType(java.net.URI.create(
                "https://foodhub.com/problems/" + type
        ));
        return problem;
    }
}
