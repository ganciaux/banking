package com.ganc.banking.exceptions;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ObjectValidationException extends RuntimeException{

    public ObjectValidationException(Set<String> violations, String violationSource) {
    }
}
