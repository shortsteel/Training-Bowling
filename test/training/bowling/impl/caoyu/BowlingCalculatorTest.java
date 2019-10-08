package training.bowling.impl.caoyu;

import org.junit.Test;

import static org.junit.Assert.*;

public class BowlingCalculatorTest {
    @Test
    public void calculate() {
        assert BowlingCalculator.calculate(new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{10}})==300;
        assert BowlingCalculator.calculate(new int[][]{{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}})==0;
        assert BowlingCalculator.calculate(new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{3,7}})==283;
        assert BowlingCalculator.calculate(new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10},{1,9},{10}})==271;
        assert BowlingCalculator.calculate(new int[][]{{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5}})==150;
        assert BowlingCalculator.calculate(new int[][]{{-1,-4},{11,-1}})==-1;
        assert BowlingCalculator.calculate(new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{3,8}})==-1;
        assert BowlingCalculator.calculate(new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{10},{7}})==297;
    }
}