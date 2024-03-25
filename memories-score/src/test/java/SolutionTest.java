import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @ParameterizedTest
    @MethodSource
    void testSolution(
            String[] names,
            int[] yearnings,
            String[][] photos,
            int[] expectedResult) {
        int[] actualResult = new Solution().solution(names, yearnings, photos);

        assertArrayEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of(
                        new String[]{"may", "kein", "kain", "radi"},
                        new int[]{5, 10, 1, 3},
                        new String[][]{
                                {"may", "kein", "kain", "radi"},
                                {"may", "kein", "brin", "deny"},
                                {"kon", "kain", "may", "coni"}},
                        new int[]{19, 15, 6}),
                Arguments.of(
                        new String[]{"kali", "mari", "don"},
                        new int[]{11, 1, 55},
                        new String[][]{
                                {"kali", "mari", "don"},
                                {"pony", "tom", "teddy"},
                                {"con", "mona", "don"}},
                        new int[]{67, 0, 55}),
                Arguments.of(
                        new String[]{"may", "kein", "kain", "radi"},
                        new int[]{5, 10, 1, 3},
                        new String[][]{
                                {"may"},
                                {"kein", "deny", "may"},
                                {"kon", "coni"}},
                        new int[]{5, 15, 0}));
    }
}
