import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final String PLUS = "2 + 3";
    private final String MINUS = "7 - 3";
    private final String MULTI = "2 * 7";
    private final String DELI = "9 / 3";

    @Test
    void plus() throws OperationNotSupportedException {
        Integer plus = SimpleCalculator.calculate(PLUS);
        assertEquals(5, plus);
    }

    @Test
    void minus() throws OperationNotSupportedException {
        Integer minus = SimpleCalculator.calculate(MINUS);
        assertEquals(4, minus);
    }

    @Test
    void multi() throws OperationNotSupportedException {
        Integer multi = SimpleCalculator.calculate(MULTI);
        assertEquals(14, multi);
    }

    @Test
    void deli() throws OperationNotSupportedException {
        Integer deli = SimpleCalculator.calculate(DELI);
        assertEquals(3, deli);
    }

    @Test
    void multiCalcPlus() throws OperationNotSupportedException {
        String multiEx = "3 + 3 + 3 + 3";
        Integer result = SimpleCalculator.calculate(multiEx);

        assertEquals(12, result);
    }

    @Test
    void multiCalcMinus() throws OperationNotSupportedException {
        String multiEx = "3 - 3 - 3 - 3";
        Integer result = SimpleCalculator.calculate(multiEx);

        assertEquals(-6, result);
    }

    @Test
    void multiCalc() throws OperationNotSupportedException {
        String multiEx = "3 * 3 / 3 + 3 - 3";
        Integer result = SimpleCalculator.calculate(multiEx);

        assertEquals(3, result);
    }

    @Test
    void multiCalcHard() throws OperationNotSupportedException {
        String ex = "4 * 2 / 4 + 21";
        Integer res = SimpleCalculator.calculate(ex);

        assertEquals(23, res);
    }

    @Test
    void calculate1() throws OperationNotSupportedException {
        String ex = "2 + 2 * 2";
        Integer res = SimpleCalculator.calculate(ex);

        assertEquals(6, res);
    }

    @Test
    void calculate2() throws OperationNotSupportedException {
        String ex = "2 + 2 * 2 / 4 + 6 - 1 + 2 * 3";
        Integer res = SimpleCalculator.calculate(ex);

        assertEquals(14, res);
    }

    @Test
    void calculateHard1() throws OperationNotSupportedException {
        String ex = "(2 + 2) * 2 / 4 + ((6 - 1 + 2) * 3)";
        Integer res = SimpleCalculator.calculate(ex);

        assertEquals(23, res);
    }

    @Test
    void calculateHard2() throws OperationNotSupportedException {
        String ex = "2 + ((2 * 2) / 4) + 6 - 1 + 2 * 3";
        Integer res = SimpleCalculator.calculate(ex);

        assertEquals(14, res);
    }

    /* @Test
    void priorityOperation() {
        String none = "2 + 3 - 2 - 1";
        String priority = "2 + 4 - 2 * 6 - 3";
        String error = SimpleCalculator.getPriorityOperation(none);
        String ok = SimpleCalculator.getPriorityOperation(priority).replace(" ", "");

        assertNull(error);
        assertEquals("2*6", ok);
    }

    @Test
    void getOperands() {
        Operands operands = SimpleCalculator.getOperands(PLUS);
        assertEquals(2, operands.getLeft());
        assertEquals(3, operands.getRight());
    }

    @Test
    void getOperator() throws OperationNotSupportedException {
        Operator plus = SimpleCalculator.getOperator(PLUS);
        Operator minus = SimpleCalculator.getOperator(MINUS);
        Operator multi = SimpleCalculator.getOperator(MULTI);
        Operator deli = SimpleCalculator.getOperator(DELI);

        assertEquals(Operator.PLUS, plus);
        assertEquals(Operator.MINUS, minus);
        assertEquals(Operator.MULTI, multi);
        assertEquals(Operator.DELI, deli);
    }

    @Test
    void operatorException() {
        assertThrows(OperationNotSupportedException.class, () -> SimpleCalculator.getOperator("2 % 3"));
    }

    @Test
    void getBiEx() {
        String operation = PLUS + " - " + MINUS;
        String ex = SimpleCalculator.getBiEx(operation).replace(" ", "");

        assertEquals(PLUS.replace(" ", ""), ex);
    }

    @Test
    void getBiExFromVoid() {
        String operation = "";
        String ex = SimpleCalculator.getBiEx(operation);

        assertNull(ex);
    } */
}