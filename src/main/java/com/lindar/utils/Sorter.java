package com.lindar.utils;

import com.lindar.ticket.Ticket;

import java.util.stream.IntStream;

public class Sorter {
    public static void sort(int[] array) {
        IntStream.range(0, array.length).forEach(x -> {
            for (int index = 0; index < array.length - 1; index++) {
                if (array[index] != Ticket.EMPTY_CELL) {
                    for (int nextElementToTest = index + 1; nextElementToTest < array.length; nextElementToTest++) {
                        if (array[nextElementToTest] != Ticket.EMPTY_CELL && array[index] > array[nextElementToTest]) {
                            int temp = array[index];
                            array[index] = array[nextElementToTest];
                            array[nextElementToTest] = temp;
                        }
                    }
                }
            }
        });
    }
}
