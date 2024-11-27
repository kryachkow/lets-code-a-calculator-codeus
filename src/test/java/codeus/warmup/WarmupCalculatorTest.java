package codeus.warmup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class WarmupCalculatorTest {

    @ParameterizedTest(name = "{index} => expression={0}, expectedResult={1}")
    @CsvSource({
            "3 + 4, 7.0",
            "10 - 2, 8.0",
            "8 * 2, 16.0",
            "9 / 3, 3.0",
            "10 / 2, 5.0"
    })
    @DisplayName("Warmup Operations Test")
    void testBasicOperations(String expression, double expectedResult) {
        double result = WarmupCalculator.calculate(expression);
        assertEquals(expectedResult, result, "Expected " + expectedResult + " but got " + result);
    }


}