package com.ganc.banking.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OperationNonPermittedException extends RuntimeException {

    public OperationNonPermittedException(String errorMsg, String operationId, String source, String dependency) {
    }
}
