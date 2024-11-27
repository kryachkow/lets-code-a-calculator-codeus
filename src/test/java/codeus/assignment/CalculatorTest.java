package codeus.assignment;

import exception.InvalidCalculusTokenException;
import exception.DivisionByZeroException;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Nested
    @Order(1)
    @DisplayName("Level 1 simple calculator")
    class BasicCalculatorTest {

        @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
        @CsvSource({
                "3 + 4, 7.0",
                "10 - 2, 8.0",
                "8 * 2, 16.0",
                "9 / 3, 3.0",
                "-6 / 2, -3.0"
        })
        @DisplayName("Basic Operations Test")
        void testBasicOperations(String expression, double expectedResult) {
            double result = calculator.calculate(expression);
            assertEquals(expectedResult, result, "Expected " + expectedResult + " but got " + result);
        }
    }


    @Nested
    @Order(2)
    @DisplayName("Level 2 exception on invalid input")
    class InputValidationTest {

        @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
        @CsvSource({
                "3 + fdd",
                "abc + 2",
                "10 * x",
        })
        @DisplayName("Input Validation Test")
        void testInputValidation(String expression) {
            assertThrows(InvalidCalculusTokenException.class, () -> calculator.calculate(expression));
        }

        @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
        @CsvSource({
                "4 / 0",
                "-7 / 0",
                "0 / 0",
        })
        @DisplayName("Input Validation Test")
        void testDivisionByZero(String expression) {
            assertThrows(DivisionByZeroException.class, () -> calculator.calculate(expression));
        }
    }

    @DisplayName("Level 3 advanced operations, sequence of '+' and '-' operands or '*' and '/'")
    @Nested
    class AdvancedOperationsTest {
        @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
        @CsvSource({
                "10 + 5 + 3, 18.0",
                "10 * 3 / 2, 15.0",
                "20 + 2 - 34, -12.0"
        })
        @DisplayName("Advanced Operations Test")
        void testAdvancedOperations(String expression, double expectedResult) {
            double result = calculator.calculate(expression);
            assertEquals(expectedResult, result, "Expected " + expectedResult + " but got " + result);
        }
    }

    @DisplayName("Level 4 support parentheses")
    @Nested
    class ParenthesesSupportTest  {
        @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
        @CsvSource({
                "( 2 + 3 ) * 4, 20.0",
                "10 - ( 2 + 3 ), 5.0",
                "( 8 * 2 ) / 4, 4.0",
                "( ( 1 + 2 ) * 3 ) - ( 4 + 5 ), 0.0"
        })
        @DisplayName("Parentheses Support Test")
        void testParenthesesSupport(String expression, double expectedResult) {
            double result = calculator.calculate(expression);
            assertEquals(expectedResult, result, "Expected " + expectedResult + " but got " + result);
        }
    }



}