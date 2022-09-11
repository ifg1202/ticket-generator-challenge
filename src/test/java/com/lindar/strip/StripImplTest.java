package com.lindar.strip;

import com.lindar.utils.RandomEngine;
import com.lindar.utils.RandomEngineImpl;
import com.lindar.ticket.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StripImplTest {

    StripImpl strip;
    RandomEngine randomEngine = new RandomEngineImpl();

    @BeforeEach
    void setUp() {
        strip = new StripImpl(randomEngine);
    }

    @Test
    void printStrip() {
        Strip strip = StripFactory.getStrip();
        strip.print();
    }

    @Test
    void getNumbersForTicket() {
        //when
        int[][] elementsForTicket = strip.getElementsForTickets();
        assertThat(elementsForTicket[0].length).isEqualTo(15);
    }

    @Test
    void testCanInsert() {
        int[] elements = {1,11,22,33,44,55,66,77,88};
        int[] insertable = strip.getInsertableColumns(elements);
        assertThat(Collections.singletonList(insertable)).containsAll(Collections.singletonList(new int[]{0,1,2,3,4,5,6,7,8}));
    }

    @Test
    void testCanInsert2() {
        int[] elements = {1,11,22,33,44,55,66,77,88,89,87};
        int[] insertable = strip.getInsertableColumns(elements);
        assertThat(Collections.singletonList(insertable)).containsAll(Collections.singletonList(new int[]{0,1,2,3,4,5,6,7}));
    }

    @Test
    void testCanInsert3() {
        int[] elements = {1,11,22,33,44,45,46,55,66,77,88,89,90};
        int[] insertable = strip.getInsertableColumns(elements);
        assertThat(Collections.singletonList(insertable)).containsAll(Collections.singletonList(new int[]{0,1,2,3,5,6,7}));
    }

    @Test
    void testSwitcheable() {
        int[] elements = {1,11,22,33,44,45,46,55,66,77,88,89,90};
        int switcheableValue = strip.getSwitcheableValue(elements);

    }

    @Test
    void isInsertable() {
        int[] elements = {1,11,22,33,44,45,46,55,66,77,88,89,90};
        assertThat(strip.isInsertableInTheColumn(elements, 3)).isTrue();
    }

    @Test
    void isNotInsertable() {
        int[] elements = {1,11,22,33,44,45,46,55,66,77,88,89,90};
        assertThat(strip.isInsertableInTheColumn(elements, 80)).isFalse();
    }

    @Test
    void addOneElementToEachColumnTimeElapsed() {
        long maxTimeToProcessMillis = 200;
        Instant start = Instant.now();
        for (int i = 0; i < 10000; i++) {
            int[][] elementsForTickets = new int[Strip.TOTAL_TICKETS][Ticket.TOTAL_ELEMENTS];
            Map<Integer, List<Integer>> stripRandomNumbers = randomEngine.getStripRandomNumberMap(Ticket.TOTAL_COLUMNS);
            strip.addOneElementToEachColumn(elementsForTickets, stripRandomNumbers);
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        //then
        assertThat(timeElapsed).isLessThanOrEqualTo(maxTimeToProcessMillis);
    }

}