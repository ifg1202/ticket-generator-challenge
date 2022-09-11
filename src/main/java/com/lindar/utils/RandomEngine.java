package com.lindar.utils;

import java.util.List;
import java.util.Map;

public interface RandomEngine {

    int getAnyFromCollection(int[] numbers);

    Map<Integer, List<Integer>> getStripRandomNumberMap(int columns);
}
