import java.util.*;

import static java.util.stream.Collectors.toCollection;

class Solution {

    public int solution(int[][] targets) {
        return new MissileInterceptionSystem(targets).strategy(MissileStrategy.minMissiles()).count();
    }
}

class MissileInterceptionSystem {

    private final SortedSet<XCoordinate> xCoordinates;

    public MissileInterceptionSystem(int[][] targets) {
        xCoordinates = Arrays.stream(targets)
                .map(XCoordinate::new)
                .collect(toCollection(TreeSet::new));
    }

    public MissileCommand strategy(MissileStrategy missileStrategy) {
        return missileStrategy.apply(xCoordinates);
    }
}

@FunctionalInterface
interface MissileStrategy {

    MissileCommand apply(SortedSet<XCoordinate> xCoordinates);

    static MissileStrategy minMissiles() {
        return xCoordinates -> () -> {
            Iterator<XCoordinate> iterator = xCoordinates.iterator();
            XCoordinate prevXCoordinate = iterator.next();
            int count = 1;
            while (iterator.hasNext()) {
                XCoordinate currXCoordinate = iterator.next();
                if (prevXCoordinate.includesStart(currXCoordinate)) {
                    prevXCoordinate = prevXCoordinate.withStart(currXCoordinate).withMinEnd(currXCoordinate);
                } else {
                    prevXCoordinate = currXCoordinate;
                    count++;
                }
            }
            return count;
        };
    }
}

@FunctionalInterface
interface MissileCommand {

    int count();
}

class XCoordinate implements Comparable<XCoordinate> {

    private final int start;
    private final int end;

    public XCoordinate(int[] xCoordinateInts) {
        this(xCoordinateInts[0], xCoordinateInts[1]);
    }

    public XCoordinate(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean includesStart(XCoordinate target) {
        return (this.start <= target.start) && (target.start < this.end);
    }

    public XCoordinate withStart(XCoordinate target) {
        return new XCoordinate(target.start, this.end);
    }

    public XCoordinate withMinEnd(XCoordinate target) {
        return new XCoordinate(this.start, Math.min(this.end, target.end));
    }

    @Override
    public int compareTo(XCoordinate target) {
        return Comparator.<XCoordinate>comparingInt(xCoordinate -> xCoordinate.start)
                .thenComparingInt(xCoordinate -> xCoordinate.end)
                .compare(this, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XCoordinate that = (XCoordinate) o;

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