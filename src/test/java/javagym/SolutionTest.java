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
        Solution solution = new Solution();
        long tsStart = System.currentTimeMillis();
        try {
            //TODO @mverleg:
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
        //TODO @mverleg:
    }

    @Test
    void testSolutionIsShortestDistance() {
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        //TODO @mverleg:
    }

    @Test
    void testSolutionWithinOriginalPoints() {
        // To prevent people from cheating by creating their own Point instances
        if (solutionException != null) {
            throw new AssertionError(solutionException);
        }
        assertNotNull(answer);
        //TODO @mverleg:
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
        assertEquals(new Point(1, 0, 0), otherAnswer.getLeft());
        assertEquals(new Point(2, 0, 0), otherAnswer.getRight());
    }
}
