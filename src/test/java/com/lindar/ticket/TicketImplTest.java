package com.lindar.ticket;

import com.lindar.utils.RandomEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketImplTest {

    TicketImpl ticket;

    RandomEngine randomEngine = mock(RandomEngine.class);

    @BeforeEach
    void setUp() {
        ticket = new TicketImpl(randomEngine);
    }

    @Test
    void addElementColumnOne() {
        int element = 1;
        int[] elements = {element};
        ticket.addElements(elements);
        assertTrue(ticket.isElementInColumn(element, 0));
    }

    @Test
    void addElementColumnTwo() {
        int element = 10;
        int[] elements = {element};
        ticket.addElements(elements);
        assertTrue(ticket.isElementInColumn(element, 1));
        assertFalse(ticket.isElementInColumn(element, 0));
    }

    @Test
    void isNRowFull() {
        int[] elements = {1, 10, 30, 40, 50};
        ticket.addElements(elements);
        assertFalse(ticket.isNotRowFull(0));
    }

    @Test
    void isNotRowFull() {
        int[] elements = {1, 10, 30, 40};
        ticket.addElements(elements);
        assertTrue(ticket.isNotRowFull(0));
    }

    @Test
    void getRowsNotFullEmptyTicket() {
        int[] nonFullRows = ticket.getNonFullRows();
        assertThat(Collections.singletonList(nonFullRows)).containsAll(Collections.singletonList(new int[]{0, 1, 2}));
    }

    @Test
    void getRowsNotFullWhenFullOneRow() {
        int[] elements = {1, 10, 30, 40, 50};
        ticket.addElements(elements);
        int[] nonFullRows = ticket.getNonFullRows();
        assertThat(Collections.singletonList(nonFullRows)).containsAll(Collections.singletonList(new int[]{1, 2}));
    }

    @Test
    void randomEngineChoicesTheThirdRowAlways() {
        int[] elements = {1, 10, 30, 40, 50};
        when(randomEngine.getAnyFromCollection(any())).thenReturn(2);
        ticket.addElements(elements);
        int[] nonFullRows = ticket.getNonFullRows();
        assertThat(Collections.singletonList(nonFullRows)).containsAll(Collections.singletonList(new int[]{0, 1}));
    }

    @Test
    void getEmptyCellsFromColumn() {
        int[] elements = {1};
        when(randomEngine.getAnyFromCollection(any())).thenReturn(2);
        ticket.addElements(elements);
        int[] emptyCellsFromColumn = ticket.getEmptyRowsFromColumn(0);
        assertThat(Collections.singletonList(emptyCellsFromColumn)).containsAll(Collections.singletonList(new int[]{0, 1}));
    }

    @Test
    void isRowOverloaded() {
        int[] elements = {1, 10, 30, 40, 50, 60, 70};
        when(randomEngine.getAnyFromCollection(any())).thenReturn(2);
        ticket.addElements(elements);
        assertTrue(ticket.isRowOverloaded(2));
    }

    @Test
    void getOverloadedRows() {
        int[] elements = {1, 10, 30, 40, 50, 60, 70};
        when(randomEngine.getAnyFromCollection(any())).thenReturn(2);
        ticket.addElements(elements);
        int[] overloadedRows = ticket.getOverloadedRows();
        assertThat(Collections.singletonList(overloadedRows)).containsAll(Collections.singletonList(new int[]{2}));
    }

    @Test
    void areThereNonFullRows() {
        int[] elements = {1, 10, 30, 40, 50};
        ticket.addElements(elements);
        assertTrue(ticket.areThereNonFullRows());
        assertThat(Collections.singletonList(ticket.getNonFullRows())).containsAll(Collections.singletonList(new int[]{1, 2}));
    }

    @Test
    void getEmptyCellsFromRow() {
        int[] elements = {1, 10, 20, 30, 40};
        ticket.addElements(elements);
        int[] emptyCellsFromRow = ticket.getEmptyColumnsFromRow(0);
        assertThat(Collections.singletonList(emptyCellsFromRow)).containsAll(Collections.singletonList(new int[]{5, 6, 7, 8}));
    }

    @Test
    void getNotEmptyCellsFromRow() {
        int[] elements = {1, 10, 20, 30, 40};
        ticket.addElements(elements);
        int[] notEmptyCellsFromRow = ticket.getNotEmptyColumnsFromRow(0);
        assertThat(Collections.singletonList(notEmptyCellsFromRow)).containsAll(Collections.singletonList(new int[]{0, 1, 2, 3, 4}));
    }

    @Test
    void getCommonElementsFromArrays() {
        int[] arrayOne = {1,2,3,4};
        int[] arrayTwo = {4,5,6,2,8,1};
        int[] commonElements = ticket.getCommonElementsFromArrays(arrayOne, arrayTwo);
        assertThat(Collections.singletonList(commonElements)).containsAll(Collections.singletonList(new int[]{1, 2, 4}));
    }

}