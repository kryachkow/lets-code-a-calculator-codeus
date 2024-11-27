package codeus.assignment;

import exception.InvalidCalculusTokenException;
import exception.DivisionByZeroException;

import java.util.ArrayDeque;

public class Calculator {

    /**
     * Those hints are for LEVEL 4 but those can be useful for lower levels
     * Parses and evaluates a mathematical expression in string form.
     *
     * This method implements the Shunting Yard algorithm and uses two stacks to manage
     * the numbers and operators. It processes the tokens of the given expression,
     * respecting the precedence of operators and handling parentheses appropriately.
     *
     * Steps to code this method:
     *
     * 1. **Initialize the Data Structures**:
     *    - Create two stacks: one for numbers (`numbers`) and one for operators (`operators`).
     *      ```java
     *      ArrayDeque<Double> numbers = new ArrayDeque<>();
     *      ArrayDeque<String> operators = new ArrayDeque<>();
     *      ```
     *
     * 2. **Tokenize the Expression**:
     *    - Split the input expression into tokens using spaces as delimiters.
     *    - Get the length of the array of tokens.
     *      ```java
     *      String[] tokens = expression.split(" ");
     *      int length = tokens.length;
     *      ```
     *
     * 3. **Process Each Token**:
     *    - Iterate through each token in the array.
     *      ```java
     *      for (int i = 0; i < length; i++) {
     *          String token = tokens[i].trim();
     *      ```
     *
     * 4. **Handle Different Kinds of Tokens**:
     *    - **Empty Tokens**: Skip any empty tokens.
     *    - **Opening Parentheses**: Push '(' onto the operators stack.
     *    - **Closing Parentheses**: Compute until '(' is found.
     *    - **Operators**: Manage operator precedence and push to operators stack.
     *    - **Numbers**: Convert to double and push to numbers stack.
     *
     * 5. **Evaluate Remaining Operations**:
     *    - Continue performing operations until the operators stack is empty.
     *      ```java
     *      while (!operators.isEmpty()) {
     *          double result = performOperation(numbers, operators);
     *          numbers.push(result);
     *      }
     *      ```
     *
     * 6. **Return the Result**:
     *    - Pop the final result from the numbers stack and return it.
     *      ```java
     *      return numbers.pop();
     *      ```
     *
     * @param expression the string representation of the mathematical expression
     * @return the result of the evaluation as a double
     * @throws InvalidCalculusTokenException if the expression contains an invalid token
     * @throws DivisionByZeroException if the expression contains a division by zero
     */
    public double calculate(String expression) {
        ArrayDeque<Double> numbers = new ArrayDeque<>();
        ArrayDeque<String> operators = new ArrayDeque<>();

        String[] tokens = expression.split(" ");
        int length = tokens.length;

        for (int i = 0; i < length; i++) {
            String token = tokens[i].trim();

            if (token.isEmpty()) {
                continue;
            }

            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    double result = performOperation(numbers, operators);
                    numbers.push(result);
                }
                operators.pop(); // Pop '(' from the stack
            } else if (OperationalStrategy.getOperation(token) != null) {
                while (!operators.isEmpty() && priority(token) <= priority(operators.peek())) {
                    double result = performOperation(numbers, operators);
                    numbers.push(result);
                }
                operators.push(token);
            } else {
                try {
                    double number = Double.parseDouble(token);
                    numbers.push(number);
                } catch (NumberFormatException e) {
                    throw new InvalidCalculusTokenException("Invalid token: " + token);
                }
            }
        }

        while (!operators.isEmpty()) {
            double result = performOperation(numbers, operators);
            numbers.push(result);
        }

        return numbers.pop();
    }

    private double performOperation(ArrayDeque<Double> numbers, ArrayDeque<String> operators) {
        if (numbers.size() < 2) {
            throw new InvalidCalculusTokenException("Insufficient operands");
        }
        double b = numbers.pop();
        double a = numbers.pop();
        String op = operators.pop();
        CalculusCommand command = OperationalStrategy.getOperation(op);
        if (command == null) {
            throw new InvalidCalculusTokenException("Unknown operator: " + op);
        }
        if (op.equals("/") && b == 0) {
            throw new DivisionByZeroException("Division by zero");
        }
        return command.execute(a, b);
    }

    private int priority(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/", "%" -> 2;
            default -> -1;
        };
    }

}
