package org.calculator;

import javax.naming.OperationNotSupportedException;

public interface Calculator {
    Double calculate(String ex) throws OperationNotSupportedException;
}
