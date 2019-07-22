package javagym;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static javagym.Input.Point;
import static javagym.Input.loadPointsFromCsv;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolutionTest {

    private static Pair<Point, Point> answer;
    private static long timeMs;
    private static Exception solutionException;
    private static Point[] originalPoints;

    @BeforeAll
    static void performSolution() {
        // Perform the computation only once, because it might be slow.

        originalPoints = loadPointsFromCsv();
        Point[] copyPoints = loadPointsFromCsv();
        assertEquals(200_000, originalPoints.length);
        Solution solution = new Solution();
        long tsStart = System.currentTimeMillis();
        try {
            answer = solution.solve(copyPoints);
        } catch (Exception ex) {
            solutionException = ex;
        }
        long tsEnd = System.currentTimeMillis();
        timeMs = tsEnd - tsStart;
    }

    @Test
    void testSolutionRunsWithoutError() {
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
    }

    @Test
    void testSolutionIsNotLongerThanTwoRandomPoints() {
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        double answerDistance = answer.getLeft().dist(answer.getRight());
        double goalDistance = originalPoints[0].dist(originalPoints[1]);
        if (answerDistance > goalDistance * 1.0001) {
            throw new IllegalStateException("This (" + answerDistance +") is not the smallest distance (" + goalDistance + " is smaller)");
        }
    }

    @Test
    void testSolutionIsShortestDistance() {
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        double answerDistance = answer.getLeft().dist(answer.getRight());
        double goalDistance = originalPoints[196].dist(originalPoints[494]);
        if (answerDistance > goalDistance * 1.0001) {
            throw new IllegalStateException("This (" + answerDistance +") is not the smallest distance (" + goalDistance + " is smaller)");
        }
        goalDistance = originalPoints[55419].dist(originalPoints[152023]);
        if (answerDistance > goalDistance * 1.0001) {
            throw new IllegalStateException("This (" + answerDistance +") is not the smallest distance (" + goalDistance + " is smaller)");
        }
    }

    @Test
    void testSolutionWithinOriginalPoints() {
        // To prevent people from cheating by creating their own Point instances
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        boolean foundLeft = false;
        for (Point point : originalPoints) {
            if (point.equals(answer.getLeft())) {
                foundLeft = true;
            }
        }
        assertTrue(foundLeft);
        boolean foundRight = false;
        for (Point point : originalPoints) {
            if (point.equals(answer.getRight())) {
                foundRight = true;
            }
        }
        assertTrue(foundRight);
    }

    @Test
    void testSolutionIsFastEnough() {
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        assertTrue(timeMs <= 500.0);
    }

    @Test
    void testSolutionCorrectWithOtherInput() {
        var inputPoints = new Point[]{
            new Point(1, 0, 0),
            new Point(0, 1, 0),
            new Point(0, 0, 1),
            new Point(2, 0, 0),
            new Point(4, 0, 0),
            new Point(-1, 0, 0)
        };
        Solution solution = new Solution();
        var otherAnswer = solution.solve(inputPoints);
        assertNotNull(otherAnswer);
        assertTrue(new Point(1, 0, 0).equals(otherAnswer.getLeft()) ||
                new Point(2, 0, 0).equals(otherAnswer.getLeft()));
        assertTrue(new Point(1, 0, 0).equals(otherAnswer.getRight()) ||
                new Point(2, 0, 0).equals(otherAnswer.getRight()));
    }
}
