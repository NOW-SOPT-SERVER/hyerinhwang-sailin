package org.sopt.demo.exception;

import lombok.Getter;
import org.sopt.demo.common.dto.ErrorMessage;
import org.sopt.demo.common.dto.ErrorResponse;

@Getter
public class BusinessException extends RuntimeException{
    private ErrorMessage errorMessage;
    public BusinessException(ErrorMessage errorMessage){
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
