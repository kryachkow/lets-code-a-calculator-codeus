package codeus.warmup;

import exception.TaskNotCompletedException;

public class WarmupCalculator {

    public static double calculate(String toCalculate) {
        throw new TaskNotCompletedException();
    }

}