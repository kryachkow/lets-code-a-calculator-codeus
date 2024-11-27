package codeus.assignment;

import java.util.HashMap;
import java.util.Map;

public class OperationalStrategy {
    private static final Map<String, CalculusCommand> operations = new HashMap<>();

    static {
        operations.put("+", Double::sum);
        operations.put("-", (a, b) -> a - b);
        operations.put("*", (a, b) -> a * b);
        operations.put("/", (a, b) -> a / b);
        operations.put("%", (a, b) -> a % b);
    }

    public static CalculusCommand getOperation(String operator) {
        return operations.get(operator);
    }
}
