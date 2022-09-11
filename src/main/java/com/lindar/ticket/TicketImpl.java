package com.lindar.ticket;

import com.lindar.utils.RandomEngine;
import com.lindar.utils.Sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class TicketImpl implements Ticket {

    private final int[][] TICKET = new int[TOTAL_COLUMNS][TOTAL_ROWS];

    private final RandomEngine randomEngine;

    TicketImpl(RandomEngine randomEngine) {
        this.randomEngine = randomEngine;
    }

    public void print() {
        for (int indexRow = 0; indexRow < TOTAL_ROWS; indexRow++) {
            for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
                printCell(indexRow, indexColumn);
            }
            System.out.println();
        }
    }

    private void printCell(int indexRow, int indexColumn) {
        System.out.printf(
                isTheLastColumn(indexColumn) ? LAST_COLUMN_FORMAT : COLUMN_FORMAT,
                TICKET[indexColumn][indexRow] == EMPTY_CELL ? STRING_EMPTY_CELL : TICKET[indexColumn][indexRow]
        );
    }

    private boolean isTheLastColumn(int indexColumn) {
        return indexColumn == TOTAL_COLUMNS - 1;
    }

    void addElements(final int[] elements) {
        for (int element: elements) {
            int column = TicketUtils.getColumnBasedOnElement(element);
            int randomEmptyRow = randomEngine.getAnyFromCollection(getEmptyRowsFromColumn(column));
            TICKET[column][randomEmptyRow] =  element;
        }
    }

    boolean isElementInColumn(final int element, final int column) {
        return Arrays.stream(TICKET[column]).anyMatch(x -> x == element);
    }

    boolean isNotRowFull(final int row) {
        int elementsInRow = 0;
        for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
            if (TICKET[indexColumn][row] != EMPTY_CELL)
                elementsInRow++;
        }
        return elementsInRow < MAX_ELEMENTS_IN_ROW;
    }

    int[] getNonFullRows() {
        List<Integer> nonFullRowList = new ArrayList<>();
        for (int indexRow = 0; indexRow < TOTAL_ROWS; indexRow++) {
            if (isNotRowFull(indexRow)) {
                nonFullRowList.add(indexRow);
            }
        }
        return nonFullRowList.stream().mapToInt(element->element).toArray();
    }

    int[] getEmptyRowsFromColumn(final int column) {
        List<Integer> emptyCellsFromColumn = new ArrayList<>();
        for (int indexRow = 0; indexRow < TOTAL_ROWS; indexRow++) {
            if (TICKET[column][indexRow] == EMPTY_CELL) {
                emptyCellsFromColumn.add(indexRow);
            }
        }
        return emptyCellsFromColumn.stream().mapToInt(element->element).toArray();
    }

    int[] getOverloadedRows() {
        List<Integer> overloadedRowList = new ArrayList<>();
        for (int indexRow = 0; indexRow < TOTAL_ROWS; indexRow++) {
            if (isRowOverloaded(indexRow)) {
                overloadedRowList.add(indexRow);
            }
        }
        return overloadedRowList.stream().mapToInt(element->element).toArray();
    }

    boolean isRowOverloaded(final int row) {
        int elementsInRow = 0;
        for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
            if (TICKET[indexColumn][row] != EMPTY_CELL)
                elementsInRow++;
        }
        return elementsInRow > MAX_ELEMENTS_IN_ROW;
    }

    void distributeCells() {
        while(areThereNonFullRows()) {
            int nonFullRow = randomEngine.getAnyFromCollection(getNonFullRows());
            int[] emptyColumnsFromRow = getEmptyColumnsFromRow(nonFullRow);
            int overloadedRow = randomEngine.getAnyFromCollection(getOverloadedRows());
            int[] notEmptyColumnsFromOverloadedRow = getNotEmptyColumnsFromRow(overloadedRow);
            int[] commonColumnsBetweenNonFullRowAndOverloadedRow = getCommonElementsFromArrays(emptyColumnsFromRow, notEmptyColumnsFromOverloadedRow);
            int columnToSwitch = randomEngine.getAnyFromCollection(commonColumnsBetweenNonFullRowAndOverloadedRow);
            TICKET[columnToSwitch][nonFullRow] = TICKET[columnToSwitch][overloadedRow];
            TICKET[columnToSwitch][overloadedRow] = EMPTY_CELL;
        }
    }

    boolean areThereNonFullRows() {
        return getNonFullRows().length != 0;
    }

    int[] getEmptyColumnsFromRow(final int row) {
        List<Integer> emptyColumnsFromRow = new ArrayList<>();
        for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
            if (TICKET[indexColumn][row] == EMPTY_CELL) {
                emptyColumnsFromRow.add(indexColumn);
            }
        }
        return emptyColumnsFromRow.stream().mapToInt(element->element).toArray();
    }

    int[] getNotEmptyColumnsFromRow(final int row) {
        List<Integer> emptyColumnsFromRow = new ArrayList<>();
        for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
            if (TICKET[indexColumn][row] != EMPTY_CELL) {
                emptyColumnsFromRow.add(indexColumn);
            }
        }
        return emptyColumnsFromRow.stream().mapToInt(element->element).toArray();
    }

    int[] getCommonElementsFromArrays(final int[] arrayOne, final int[] arrayTwo) {
        List<Integer> commonElements = new ArrayList<>();
        for (int elementArrayOne : arrayOne) {
            for (int elementArrayTwo : arrayTwo) {
                if (elementArrayOne == elementArrayTwo)
                    commonElements.add(elementArrayTwo);
            }
        }
        return commonElements.stream().mapToInt(element->element).toArray();
    }

    void sortColumns() {
        for (int indexColumn = 0; indexColumn < TOTAL_COLUMNS; indexColumn++) {
            Sorter.sort(TICKET[indexColumn]);
        }
    }
}
