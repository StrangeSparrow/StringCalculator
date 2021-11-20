import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SimpleCalculator {
    private static final String PRIORITY_EX_PATTERN = "\\(-?\\d+(\\.\\d)?\\d*([+,\\-*/]\\d+(\\.\\d)?\\d*)*\\)";

    public static Double calculate(String ex) throws OperationNotSupportedException {
        String resEx = ex.replace(" ", "").replace(",", ".");

        if (isBiEx(resEx)) {
            return biCalc(resEx);
        }

        Pattern p = Pattern.compile(PRIORITY_EX_PATTERN);
        Matcher m;
        Double priorityRes;
        String priorityEx;

        while (!isBiEx(resEx)) {
            m = p.matcher(resEx);

            if (m.find()) {
                priorityEx = m.group().replaceAll("[(,)]", "");
                priorityRes = multiCalc(priorityEx);
                resEx = resEx.replaceFirst(PRIORITY_EX_PATTERN, String.valueOf(priorityRes));
            } else {
                break;
            }
        }
        return multiCalc(resEx);
    }

    private static Double multiCalc(String ex) throws OperationNotSupportedException {
        String resEx = ex;
        String biEx;
        Double biRes;

        while (!isBiEx(resEx)) {
            biEx = getPriorityOperation(resEx);
            if (biEx == null)
                break;

            biRes = biCalc(biEx);
            resEx = resEx.replaceFirst("\\d+(\\.\\d)?\\d*[*,/]\\d+(\\.\\d)?\\d*", String.valueOf(biRes));
        }

        while (!isBiEx(resEx)) {
            biEx = getBiEx(resEx);
            if (biEx == null)
                break;

            biRes = biCalc(biEx);
            resEx = resEx.replaceFirst("(^\\d+(\\.\\d)?\\d*[+,\\-*/]\\d+(\\.\\d)?\\d*)", String.valueOf(biRes));
        }
        return biCalc(resEx);
    }

    private static String getPriorityOperation(String ex) {
        Pattern p = Pattern.compile("\\d+(\\.\\d)?\\d*[*,/]\\d+(\\.\\d)?\\d*+");
        Matcher m = p.matcher(ex);

        if (m.find()) {
            return m.group();
        }
        return null;
    }

    private static String getBiEx(String ex) {
        Pattern p = Pattern.compile("^\\d+(\\.\\d)?\\d*+[+,\\-*/]\\d+(\\.\\d)?\\d*");
        Matcher m = p.matcher(ex);

        if (m.find())
            return m.group();
        else
            return null;
    }

    private static Double biCalc(String ex) throws OperationNotSupportedException {
        Operands operands = getOperands(ex);
        Operator operator = getOperator(ex);

        return getResult(operands, operator);
    }

    private static Operands getOperands(String ex) {
        Pattern p = Pattern.compile("(^-)?\\d+(\\.\\d)?\\d*");
        Matcher m = p.matcher(ex);

        List<Double> results = m.results()
                .map(MatchResult::group)
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        if (results.size() != 2)
            throw new IllegalArgumentException();

        Operands operands = new Operands();
        operands.setLeft(results.get(0));
        operands.setRight(results.get(1));
        return operands;
    }

    private static Operator getOperator(String ex) throws OperationNotSupportedException {
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

    private static Double getResult(Operands operands, Operator operator) {
        switch (operator) {
            case PLUS: return operands.getLeft() + operands.getRight();
            case MINUS: return operands.getLeft() - operands.getRight();
            case MULTI: return operands.getLeft() * operands.getRight();
            case DELI: {
                if (!operands.getRight().equals(0d) || operands.getRight() == 0d)
                    return operands.getLeft() / operands.getRight();
            }
            default: throw new IllegalArgumentException();
        }
    }


    private static boolean isBiEx(String ex) {
        String resEx = ex.replace(" ", "");
        Pattern p = Pattern.compile("^\\d+(\\.\\d)?\\d*[+,\\-*/]\\d+(\\.\\d)?\\d*$");
        Matcher m = p.matcher(resEx);

        return m.find();
    }
}
