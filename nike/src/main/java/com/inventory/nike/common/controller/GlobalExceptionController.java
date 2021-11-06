//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.nike.common.controller;


import com.inventory.nike.common.em.ErrorCodeEnum;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.common.vo.ResultVO;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.text.ParseException;

@RestControllerAdvice
public class GlobalExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    public GlobalExceptionController() {
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResultVO handleNotFoundException(NoHandlerFoundException e) {
        logger.info(e.getMessage(), e);
        return this.error((ErrorCodeEnum) ErrorCodeEnum.NOT_FOUND, (Exception)e);
    }

    @ExceptionHandler({BusinessException.class})
    public ResultVO handleBusinessException(BusinessException e) {
        logger.info("BusinessException[code: {}, message: {}]", new Object[]{e.getCode(), e.getMessage(), e});
        return e.getCode() == null ? this.error(ErrorCodeEnum.BUSINESS.getCode(), e.getMessage() == null ? e.getCause().getMessage() : e.getMessage(), e.getData()) : this.error(e.getCode(), e.getMessage(), e.getData());
    }

    @ExceptionHandler({ParseException.class})
    public ResultVO handleParseException(ParseException e) {
        logger.error(e.getMessage(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.PARAMS_PARSE, (Exception)e);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResultVO handleIllegalStateException(IllegalStateException e) {
        logger.error(e.getMessage(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.BAD_REQUEST, (Exception)e);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResultVO handleException(MaxUploadSizeExceededException e) {
        logger.info(ErrorCodeEnum.MAX_UPLOAD_SIZE.getMsg(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.MAX_UPLOAD_SIZE, (Exception)e);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResultVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.info(ErrorCodeEnum.METHOD_NOT_ALLOWED.getMsg(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.METHOD_NOT_ALLOWED, (Exception)e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultVO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(ErrorCodeEnum.BAD_REQUEST.getMsg(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.BAD_REQUEST, (Exception)e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SQLSyntaxErrorException.class})
    public ResultVO handleSqlSyntaxErrorException(SQLSyntaxErrorException e) {
        logger.error(ErrorCodeEnum.SQL_ERROR_INFO.getMsg(), e);
        return this.error((ErrorCodeEnum)ErrorCodeEnum.SQL_ERROR_INFO, (Exception)e);
    }

    @ExceptionHandler({MyBatisSystemException.class})
    public ResultVO handleMyBatisSystemException(MyBatisSystemException e) {
        logger.error(ErrorCodeEnum.SQL_ERROR_INFO.getMsg(), e);
        return this.error(ErrorCodeEnum.SQL_ERROR_INFO.getCode(), ErrorCodeEnum.SQL_ERROR_INFO.getMsg().replace("{info}", e.getCause().getLocalizedMessage()), e);
    }

    @ExceptionHandler({BadSqlGrammarException.class})
    public ResultVO handleBadSqlGrammarException(BadSqlGrammarException e) {
        logger.error(ErrorCodeEnum.SQL_ERROR_INFO.getMsg(), e);
        return this.error(ErrorCodeEnum.SQL_ERROR_INFO.getCode(), ErrorCodeEnum.SQL_ERROR_INFO.getMsg().replace("{info}", e.getCause().getLocalizedMessage()), e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultVO handleMyBatisSystemException(MethodArgumentNotValidException e) {
        StringBuilder errorInfo = new StringBuilder();
        StringBuilder errorField = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();

        for(int i = 0; i < bindingResult.getFieldErrors().size(); ++i) {
            if (i > 0) {
                errorInfo.append(",");
                errorField.append(",");
            }

            FieldError fieldError = (FieldError)bindingResult.getFieldErrors().get(i);
            errorField.append(fieldError.getField());
            errorInfo.append(fieldError.getDefaultMessage());
        }

        return this.error(ErrorCodeEnum.BUSINESS.getCode(), errorInfo.toString(), errorField.toString());
    }

    @ExceptionHandler({Exception.class})
    public ResultVO handleException(Exception e) {
        logger.error(ErrorCodeEnum.SYS_ERROR.getMsg(), e);
        return this.error(ErrorCodeEnum.SYS_ERROR, e);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResultVO handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return this.error(ErrorCodeEnum.SQL_ERROR_INFO.getCode(), e.getMessage(), e);
    }

    private ResultVO error(String code, String msg) {
        return this.error(code, msg, (Object)null);
    }

    private ResultVO error(String code, String msg, Object e) {
        ResultVO vo = new ResultVO();
        vo.setMessage(msg);
        vo.setCode(code);
        vo.setData(e);
        return vo;
    }

    private ResultVO error(ErrorCodeEnum em) {
        return this.error((ErrorCodeEnum)em, (Exception)null);
    }

    private ResultVO error(ErrorCodeEnum em, Exception e) {
        return this.error(em.getCode(), em.getMsg(), e);
    }
}
