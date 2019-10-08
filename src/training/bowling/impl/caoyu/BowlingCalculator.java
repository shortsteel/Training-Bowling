package training.bowling.impl.caoyu;

public class BowlingCalculator {
    public static void main(String[] args) {
        System.out.println(BowlingCalculator.calculate(new int[][]{{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}}) == 300);
        System.out.println(BowlingCalculator.calculate(new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}) == 0);
        System.out.println(BowlingCalculator.calculate(new int[][]{{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {3, 7}}) == 283);
        System.out.println(BowlingCalculator.calculate(new int[][]{{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {1, 9}, {10}}) == 271);
        System.out.println(BowlingCalculator.calculate(new int[][]{{5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5, 5}, {5}}) == 150);
        System.out.println(BowlingCalculator.calculate(new int[][]{{-1, -4}, {11, -1}}) == -1);
        System.out.println(BowlingCalculator.calculate(new int[][]{{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {3, 8}}) == -1);
        System.out.println(BowlingCalculator.calculate(new int[][]{{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {7}}) == 297);
    }

    public static int calculate(int[][] turns) {
        if (!isLegalInput(turns))
            return -1;
        return scoreCalculator(turns);
    }

    private static boolean isLegalInput(int[][] turns) {
        //call the validation checkers
        if (!isGameValid(turns))
            return false;
        if (!isTurnsValid(turns))
            return false;
        if (!isFinalTurnValid(turns))
            return false;

        return true;
    }

    private static boolean isGameValid(int[][] turns) {
        boolean isLegal = true;

        //check turns number, the total turns number of the game must be between 10-12
        int turnNumber = turns.length;
        if (turnNumber < 10 && turnNumber > 12)
            isLegal = false;

        return isLegal;
    }

    private static boolean isTurnsValid(int[][] turns) {
        boolean isLegal = true;

        //check digit validation of every turn
        for (int i = 0; i < turns.length; i++) {
            int turnsNumberOfTheGame = turns[i].length;

            if (turnsNumberOfTheGame > 2 || turnsNumberOfTheGame <= 0)//shot time of any turn should be 1 or 2
                isLegal = false;

            for (int j = 0; j < turns[i].length; j++) {
                if (turnsNumberOfTheGame == 2) {
                    if ((turns[i][0] + turns[i][1]) > 10)//the score of every turn should not be over 10
                        isLegal = false;
                }
                if (turns[i][j] < 0)//score of any shot should no be negative number
                    isLegal = false;
            }
        }
        return isLegal;
    }

    private static boolean isFinalTurnValid(int[][] turns) {
        boolean isLegal = true;

        if (turns[9].length == 1) {//if 10th turn has one shot
            if (turns[9][0] != 10 && turns.length > 10)//if 10th turn is not a strike yet the game has more than 10 turns, the input is not valid
                isLegal = false;
            else if (turns[9][0] == 10) {//if the 10 th turn is a strike, the next 2 shots could be done in 1 or 2 extra turns
                if (turns.length == 11 && turns[10].length != 2)//bonus shots done in 1 turn
                    isLegal = false;
                else if (turns.length == 12 && (turns[10].length + turns[11].length) != 2)//bonus shots done in 2 turns
                    isLegal = false;
            }
        } else if ((turns[9][0] + turns[9][1]) == 10) {//10th turn spare validation check
            if (turns.length > 11 || turns[10].length > 1)
                isLegal = false;
        } else if ((turns[9][0] + turns[9][1]) < 10) {//10th turn miss validation check
            //if the 10th turn is a miss, no extra turns should be added
            if (turns.length > 10)
                isLegal = false;
        }
        return isLegal;
    }


    private static int scoreCalculator(int[][] turns) {
        int totalScore = 0;
        totalScore += totalNoneFinalTurnScore(turns);
        totalScore += finalTurnScore(turns);
        return totalScore;
    }

    private static int totalNoneFinalTurnScore(int[][] turns) {
        int totalNoneFinalTurnScore = 0;
        for (int i = 0; i < 9; i++) {
            if (turns[i].length == 2) {
                if ((turns[i][0] + turns[i][1]) < 10) {
                    totalNoneFinalTurnScore += noneFinalTurnMissScore(turns, i);
                } else if ((turns[i][0] + turns[i][1]) == 10) {
                    totalNoneFinalTurnScore += noneFinalTurnSpareScore(turns, i);
                }
            } else if (turns[i].length == 1 && turns[i][0] == 10) {
                totalNoneFinalTurnScore += noneFinalTurnStrikeScore(turns, i);
            }

        }
        return totalNoneFinalTurnScore;
    }

    private static int noneFinalTurnMissScore(int[][] turns, int TurnCount) {
        return turns[TurnCount][0] + turns[TurnCount][1];
    }

    private static int noneFinalTurnSpareScore(int[][] turns, int TurnCount) {
        return 10 + turns[TurnCount + 1][0];
    }

    private static int noneFinalTurnStrikeScore(int[][] turns, int TurnCount) {
        if (turns[TurnCount + 1].length == 1) {
            return 10 + turns[TurnCount + 1][0] + turns[TurnCount + 2][0];
        } else {
            return 10 + turns[TurnCount + 1][0] + turns[TurnCount + 1][1];
        }
    }

    private static int finalTurnScore(int[][] turns) {
        int finalTurnScore = 0;
        if (turns[9].length == 1) {//final Turn strike
            if (turns[10].length == 1)
                finalTurnScore = 10 + turns[10][0] + turns[11][0];
            else if (turns[10].length == 2)
                finalTurnScore = 10 + turns[10][0] + turns[10][1];
        } else if (turns[9].length == 2 && (turns[9][0] + turns[9][1]) == 10) {//final Turn spare
            finalTurnScore = 10 + turns[10][0];
        } else if (turns[9].length == 2 && (turns[9][0] + turns[9][1]) < 10) {//final Turn miss
            finalTurnScore = turns[9][0] + turns[9][1];
        }
        return finalTurnScore;
    }
}
