import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public String[] solution(String[] players, String[] callings) {
        RunningRace runningRace = RunningRace.from(players);

        runningRace.call(callings);

        return runningRace.ranks();
    }

    private static class RunningRace {

        private final Map<String, Integer> nameToRank;
        private final Map<Integer, String> rankToName;

        public RunningRace(Map<String, Integer> nameToRank, Map<Integer, String> rankToName) {
            this.nameToRank = nameToRank;
            this.rankToName = rankToName;
        }

        public static RunningRace from(String[] players) {
            Map<String, Integer> nameToRank = createNameIndex(players);
            Map<Integer, String> rankToName = createRankIndex(players);

            return new RunningRace(nameToRank, rankToName);
        }

        private static Map<String, Integer> createNameIndex(String[] players) {
            return RunningRace.<String, Integer>indexer().apply(indexingByName()).apply(players);
        }

        private static Map<Integer, String> createRankIndex(String[] players) {
            return RunningRace.<Integer, String>indexer().apply(indexingByRank()).apply(players);
        }

        private static <K, V> Function<BiFunction<String, Integer, Map.Entry<K, V>>, Function<String[], Map<K, V>>> indexer() {
            return indexing ->
                    players -> IntStream.range(0, players.length)
                            .mapToObj(i -> indexing.apply(players[i], i))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        private static BiFunction<String, Integer, Map.Entry<String, Integer>> indexingByName() {
            return (name, rank) -> Map.entry(name, rank);
        }

        private static BiFunction<String, Integer, Map.Entry<Integer, String>> indexingByRank() {
            return (name, rank) -> Map.entry(rank, name);
        }

        public void call(String... callings) {
            Arrays.stream(callings).forEachOrdered(this::call);
        }

        private void call(String name) {
            Integer rank = nameToRank.merge(name, -1, Integer::sum);
            String oldName = rankToName.replace(rank, name);
            Integer oldRank = nameToRank.merge(oldName, 1, Integer::sum);
            rankToName.replace(oldRank, oldName);
        }

        public String[] ranks() {
            return rankToName.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .toArray(String[]::new);
        }
    }
}
