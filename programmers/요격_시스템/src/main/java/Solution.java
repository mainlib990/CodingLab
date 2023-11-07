import java.util.*;

import static java.util.stream.Collectors.toCollection;

class Solution {

    public int solution(int[][] targets) {
        return new MissileInterceptionSystem(targets).strategy(MissileStrategy.minMissiles()).count();
    }
}

class MissileInterceptionSystem {

    private final SortedSet<Missile> missiles;

    public MissileInterceptionSystem(int[][] targets) {
        missiles = Arrays.stream(targets)
                .map(Missile::new)
                .collect(toCollection(TreeSet::new));
    }

    public MissileCommand strategy(MissileStrategy missileStrategy) {
        return missileStrategy.apply(missiles);
    }
}

@FunctionalInterface
interface MissileStrategy {

    MissileCommand apply(SortedSet<Missile> missiles);

    static MissileStrategy minMissiles() {
        return missiles -> () -> {
            Iterator<Missile> missileIterator = missiles.iterator();
            Missile prevMissile = missileIterator.next();
            int missileCount = 1;
            while (missileIterator.hasNext()) {
                Missile currMissile = missileIterator.next();
                if (prevMissile.includesStart(currMissile)) {
                    prevMissile = prevMissile.withStart(currMissile).withMinEnd(currMissile);
                } else {
                    prevMissile = currMissile;
                    missileCount++;
                }
            }
            return missileCount;
        };
    }
}

@FunctionalInterface
interface MissileCommand {

    int count();
}

class Missile implements Comparable<Missile> {

    private final int start;
    private final int end;

    public Missile(int[] missileInts) {
        this(missileInts[0], missileInts[1]);
    }

    public Missile(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean includesStart(Missile target) {
        return (this.start <= target.start) && (target.start < this.end);
    }

    public Missile withStart(Missile target) {
        return new Missile(target.start, this.end);
    }

    public Missile withMinEnd(Missile target) {
        return new Missile(this.start, Math.min(this.end, target.end));
    }

    @Override
    public int compareTo(Missile target) {
        return Comparator.<Missile>comparingInt(missile -> missile.start)
                .thenComparingInt(missile -> missile.end)
                .compare(this, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Missile that = (Missile) o;

        if (start != that.start) return false;
        return end == that.end;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        return result;
    }
}