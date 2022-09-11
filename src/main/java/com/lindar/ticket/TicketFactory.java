package com.lindar.ticket;

import com.lindar.utils.RandomEngine;
import com.lindar.utils.RandomEngineImpl;

final public class TicketFactory {

    private static final RandomEngine randomEngine = new RandomEngineImpl();

    public static Ticket getTicket(final int[] elements) {
        TicketImpl ticket = new TicketImpl(randomEngine);
        ticket.addElements(elements);
        ticket.distributeCells();
        ticket.sortColumns();
        return ticket;
    }

}
