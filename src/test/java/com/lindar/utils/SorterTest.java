package com.lindar.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class SorterTest {

    @Test
    void sortThreeElements() {
        int[] array = {3,2,1};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(1,2,3);
    }

    @Test
    void sortThreeElementsWithZero() {
        int[] array = {0,3,2};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(0,2,3);
    }

    @Test
    void sortThreeElementsWithZeroBegin() {
        int[] array = {0,3,2};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(0,2,3);
    }

    @Test
    void sortTwoZeroBeginning() {
        int[] array = {0,0,2};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(0,0,2);
    }

    @Test
    void sortThreeElementsWithZeroInTheMiddle() {
        int[] array = {3,0,2};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(2,0,3);
    }

    @Test
    void sortThreeElementsWithZeroInTheMiddleSame() {
        int[] array = {2,0,3};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(2,0,3);
    }

    @Test
    void sortThreeElementsWithZeroInTheEnd() {
        int[] array = {3,2,0};
        Sorter.sort(array);
        assertThat(array).containsSubsequence(2,3,0);
    }

}
