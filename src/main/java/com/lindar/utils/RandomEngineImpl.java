package com.lindar.utils;
import com.lindar.ticket.Ticket;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class RandomEngineImpl implements RandomEngine {

    public static final int FIRST_COLUMN = 0;
    public static final int FIRST_COLUMN_LOWER_BOUND = 1;
    public static final int FIRST_COLUMN_UPPER_BOUND = 10;
    public static final int LAST_COLUMN = 8;

    private final Random random = new Random();

    @Override
    public int getAnyFromCollection(int[] numbers) {
        return numbers[random.nextInt(numbers.length)];
    }

    @Override
    public Map<Integer, List<Integer>> getStripRandomNumberMap(int columns) {
        Map<Integer, List<Integer>> stripRandomNumbers = new HashMap<>();
        IntStream.range(FIRST_COLUMN, columns).forEach(x -> stripRandomNumbers.put(x, getRange(x)));
        return stripRandomNumbers;
    }

    List<Integer> getRandomNumbersForColumn(int column) {
        return getRange(column);
    }

    private List<Integer> getRange(int column) {
        List<Integer> range = IntStream.range(getColumnLowerBound(column), getColumnUpperBound(column)).boxed()
                .collect(Collectors.toList());
        Collections.shuffle(range);
        return range;
    }

    private int getColumnLowerBound(int column) {
        int columnLowerBound = column * Ticket.INDEX_COLUMN_FACTOR;
        return column == FIRST_COLUMN ? FIRST_COLUMN_LOWER_BOUND : columnLowerBound;
    }

    private int getColumnUpperBound(int column) {
        int columnUpperBound = column == LAST_COLUMN ? ((column + 1) * Ticket.INDEX_COLUMN_FACTOR) + 1 : (column + 1) * Ticket.INDEX_COLUMN_FACTOR;
        return column == FIRST_COLUMN ? FIRST_COLUMN_UPPER_BOUND : columnUpperBound;
    }
}
