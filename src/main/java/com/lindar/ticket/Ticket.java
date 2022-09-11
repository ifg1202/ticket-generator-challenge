package com.lindar.ticket;

public interface Ticket {

    int TOTAL_COLUMNS = 9;
    int TOTAL_ROWS = 3;
    int MAX_ELEMENTS_IN_ROW = 5;
    int EMPTY_CELL = 0;
    int INDEX_COLUMN_FACTOR = 10;
    int TOTAL_ELEMENTS = 15;

    String LAST_COLUMN_FORMAT = "|%2s|";
    String COLUMN_FORMAT = "|%2s";
    String STRING_EMPTY_CELL = " ";

    void print();
}
