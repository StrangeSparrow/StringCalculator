import javax.naming.OperationNotSupportedException;

public interface Calculator<T extends Number> {
    T calculate(String ex) throws OperationNotSupportedException;
}
