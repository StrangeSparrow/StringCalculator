import org.calculator.impl.SimpleCalculator;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void plus() throws OperationNotSupportedException {
        String PLUS = "2 + 3";
        Double plus = SimpleCalculator.calculate(PLUS);
        assertEquals(5, plus);
    }

    @Test
    void minus() throws OperationNotSupportedException {
        String MINUS = "7 - 3";
        Double minus = SimpleCalculator.calculate(MINUS);
        assertEquals(4, minus);
    }

    @Test
    void multi() throws OperationNotSupportedException {
        String MULTI = "2 * 7";
        Double multi = SimpleCalculator.calculate(MULTI);
        assertEquals(14, multi);
    }

    @Test
    void deli() throws OperationNotSupportedException {
        String DELI = "9 / 3";
        Double deli = SimpleCalculator.calculate(DELI);
        assertEquals(3, deli);
    }

    @Test
    void multiCalcPlus() throws OperationNotSupportedException {
        String multiEx = "3 + 3 + 3 + 3";
        Double result = SimpleCalculator.calculate(multiEx);

        assertEquals(12, result);
    }

    @Test
    void multiCalcMinus() throws OperationNotSupportedException {
        String multiEx = "3 - 3 - 3 - 3";
        Double result = SimpleCalculator.calculate(multiEx);

        assertEquals(-6, result);
    }

    @Test
    void multiCalc() throws OperationNotSupportedException {
        String multiEx = "3 * 3 / 3 + 3 - 3";
        Double result = SimpleCalculator.calculate(multiEx);

        assertEquals(3, result);
    }

    @Test
    void multiCalcHard() throws OperationNotSupportedException {
        String ex = "4 * 2 / 4 + 21";
        Double res = SimpleCalculator.calculate(ex);

        assertEquals(23, res);
    }

    @Test
    void calculate1() throws OperationNotSupportedException {
        String ex = "2 + 2 * 2";
        Double res = SimpleCalculator.calculate(ex);

        assertEquals(6, res);
    }

    @Test
    void calculate2() throws OperationNotSupportedException {
        String ex = "2 + 2 * 2 / 4 + 6 - 1 + 2 * 3";
        Double res = SimpleCalculator.calculate(ex);

        assertEquals(14, res);
    }

    @Test
    void calculateHard1() throws OperationNotSupportedException {
        String ex = "(2 + 2) * 2 / 4 + ((6 - 1 + 2) * 3)";
        Double res = SimpleCalculator.calculate(ex);

        assertEquals(23, res);
    }

    @Test
    void calculateHard2() throws OperationNotSupportedException {
        String ex = "2.0 + ((2,0 * 2.0) / 4.0) + 6,0 - 1 + 2 * 3";
        Double res = SimpleCalculator.calculate(ex);

        assertEquals(14, res);
    }
}