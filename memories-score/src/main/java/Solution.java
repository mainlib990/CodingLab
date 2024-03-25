import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public int[] solution(String[] names, int[] yearnings, String[][] photos) {
        MemoriesScore memoriesScore = MemoriesScore.of(names, yearnings);

        return memoriesScore.scores(photos);
    }

    private static class MemoriesScore {

        private final Map<String, Integer> nameToYearning;

        public MemoriesScore(Map<String, Integer> nameToYearning) {
            this.nameToYearning = nameToYearning;
        }

        public static MemoriesScore of(String[] names, int[] yearnings) {
            Map<String, Integer> nameToYearning = IntStream.range(0, Math.min(names.length, yearnings.length))
                    .mapToObj(i -> Map.entry(names[i], yearnings[i]))
                    .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

            return new MemoriesScore(nameToYearning);
        }

        public int[] scores(String[][] photos) {
            return Arrays.stream(photos)
                    .mapToInt(this::score)
                    .toArray();
        }

        public int score(String... photos) {
            return Arrays.stream(photos)
                    .mapToInt(photo -> nameToYearning.getOrDefault(photo, 0))
                    .sum();
        }
    }
}
