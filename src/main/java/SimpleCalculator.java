import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SimpleCalculator {
    public static Integer calculate(String ex) throws OperationNotSupportedException {
        String resEx = ex.replace(" ", "");

        if (isBiEx(resEx)) {
            return biCalc(resEx);
        }

        Pattern p = Pattern.compile("\\(-?\\d+([+,\\-*/]\\d+)*\\)");
        Matcher m;
        Integer priorityRes;
        String priorityEx;

        while (!isBiEx(resEx)) {
            m = p.matcher(resEx);

            if (m.find()) {
                priorityEx = m.group().replaceAll("[(,)]", "");
                priorityRes = multiCalc(priorityEx);
                resEx = resEx.replaceFirst("\\(-?\\d+([+,\\-*/]\\d+)*\\)", String.valueOf(priorityRes));
            } else {
                break;
            }
        }
        return multiCalc(resEx);
    }

    public static Integer multiCalc(String ex) throws OperationNotSupportedException {
        String resEx = ex.replace(" ", "");
        String biEx;
        Integer biRes;

        while (!isBiEx(resEx)) {
            biEx = getPriorityOperation(resEx);
            if (biEx == null)
                break;

            biRes = biCalc(biEx);
            resEx = resEx.replaceFirst("\\d+[*,/]\\d+", String.valueOf(biRes));
        }

        while (!isBiEx(resEx)) {
            biEx = getBiEx(resEx);
            if (biEx == null)
                break;

            biRes = biCalc(biEx);
            resEx = resEx.replaceFirst("(^\\d+[+,\\-*/]\\d+)", String.valueOf(biRes));
        }
        return biCalc(resEx);
    }

    public static String getPriorityOperation(String ex) {
        String resEx = ex.replace(" ", "");
        Pattern p = Pattern.compile("[\\d]+[*,/][\\d]+");
        Matcher m = p.matcher(resEx);

        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static String getBiEx(String ex) {
        String resEx = ex.replace(" ", "");

        Pattern p = Pattern.compile("^[\\d]+[+,\\-*/][\\d]+");
        Matcher m = p.matcher(resEx);

        if (m.find())
            return m.group();
        else
            return null;
    }

    public static Integer biCalc(String ex) throws OperationNotSupportedException {
        Operands operands = getOperands(ex);
        Operator operator = getOperator(ex);

        return getResult(operands, operator);
    }

    public static Operands getOperands(String ex) {
        String resEx = ex.replace(" ", "");

        Pattern p = Pattern.compile("(^-)?\\d+");
        Matcher m = p.matcher(resEx);

        List<Integer> results = m.results()
                .map(MatchResult::group)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (results.size() != 2)
            throw new IllegalArgumentException();

        Operands operands = new Operands();
        operands.setLeft(results.get(0));
        operands.setRight(results.get(1));
        return operands;
    }

    public static Operator getOperator(String ex) throws OperationNotSupportedException {
        Pattern p = Pattern.compile("[+,\\-*/]");
        Matcher m = p.matcher(ex);

        String op;
        if (m.find())
            op = m.group();
        else
            throw new OperationNotSupportedException(ex);

        switch (op) {
            case CalculatorConst.PLUS:
                return Operator.PLUS;
            case CalculatorConst.MINUS:
                return Operator.MINUS;
            case CalculatorConst.MULTI:
                return Operator.MULTI;
            case CalculatorConst.DELI:
                return Operator.DELI;
        }
        throw new OperationNotSupportedException(ex);
    }

    private static Integer getResult(Operands operands, Operator operator) {
        return switch (operator) {
            case PLUS -> operands.getLeft() + operands.getRight();
            case MINUS -> operands.getLeft() - operands.getRight();
            case MULTI -> operands.getLeft() * operands.getRight();
            case DELI -> operands.getLeft() / operands.getRight();
        };
    }


    private static boolean isBiEx(String ex) {
        String resEx = ex.replace(" ", "");
        Pattern p = Pattern.compile("^[\\d]+[+,\\-*/][\\d]+$");
        Matcher m = p.matcher(resEx);

        return m.find();
    }
}
