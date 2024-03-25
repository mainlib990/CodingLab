import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void testSolution() {
        String[] players = new String[]{"mumu", "soe", "poe", "kai", "mine"};
        String[] callings = new String[]{"kai", "kai", "mine", "mine"};
        String[] expectedResult = new String[]{"mumu", "kai", "mine", "soe", "poe"};

        String[] actualResult = new Solution().solution(players, callings);

        assertArrayEquals(expectedResult, actualResult);
    }
}
