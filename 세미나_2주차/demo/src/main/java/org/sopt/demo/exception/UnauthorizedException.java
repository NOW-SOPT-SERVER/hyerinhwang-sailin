package org.sopt.demo.exception;

import org.sopt.demo.common.dto.ErrorMessage;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
