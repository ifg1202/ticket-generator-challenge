package com.lindar.utils;

import com.lindar.utils.RandomEngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RandomEngineImplTest {

    RandomEngineImpl randomEngine;

    @BeforeEach
    void setUp() {
        randomEngine = new RandomEngineImpl();
    }

    @Test
    void getRandomNumbersForColumnOne() {
        int columnOne = 0;
        List<Integer> randomNumbers = randomEngine.getRandomNumbersForColumn(columnOne);
        assertThat(randomNumbers)
                .isNotEmpty()
                .hasSize(9)
                .doesNotHaveDuplicates()
                .contains(1,2,3,4,5,6,7,8,9);
    }

    @Test
    void getRandomNumbersForColumnTwo() {
        int columnTwo = 1;
        List<Integer> randomNumbers = randomEngine.getRandomNumbersForColumn(columnTwo);
        assertThat(randomNumbers)
                .isNotEmpty()
                .hasSize(10)
                .doesNotHaveDuplicates()
                .contains(10,12,13,14,15,16,17,18,19);
    }

    @Test
    void getRandomNumbersForColumnThree() {
        int columnThree = 2;
        List<Integer> randomNumbers = randomEngine.getRandomNumbersForColumn(columnThree);
        assertThat(randomNumbers)
                .isNotEmpty()
                .hasSize(10)
                .doesNotHaveDuplicates()
                .contains(20,22,23,24,25,26,27,28,29);
    }

    @Test
    void getRandomNumbersForColumnNine() {
        int columnNine = 8;
        List<Integer> randomNumbers = randomEngine.getRandomNumbersForColumn(columnNine);
        assertThat(randomNumbers)
                .isNotEmpty()
                .hasSize(11)
                .doesNotHaveDuplicates()
                .contains(80,81,82,83,84,85,86,87,88,89,90);
    }

    @Test
    void getRandomNumberMapForColumnNineColumns() {
        int columns = 9;
        Map<Integer, List<Integer>> stripRandomMap = randomEngine.getStripRandomNumberMap(columns);
        IntStream.range(0, columns).forEach(x -> assertThat(stripRandomMap.get(x)).isNotEmpty());
    }

}