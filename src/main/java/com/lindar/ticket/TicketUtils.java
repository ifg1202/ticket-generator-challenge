package com.lindar.ticket;

public class TicketUtils {

    public static int getColumnBasedOnElement(int element) {
        int EXTRA_ELEMENT_COLUMN = Ticket.TOTAL_COLUMNS * Ticket.INDEX_COLUMN_FACTOR;
        int LAST_COLUMN = Ticket.TOTAL_COLUMNS - 1;
        return element == EXTRA_ELEMENT_COLUMN ? LAST_COLUMN : getColumnFromElement(element);
    }

    private static int getColumnFromElement(int element) {
        return element/Ticket.INDEX_COLUMN_FACTOR;
    }
}
