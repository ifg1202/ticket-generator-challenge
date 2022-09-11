package com.lindar.ticket;

import org.junit.jupiter.api.Test;

class TicketFactoryTest {

    @Test
    void testFactory() {
        int[] elements = {1, 10, 20, 30, 40, 50, 60, 70, 80, 2, 13, 21, 32, 43, 52};
        Ticket ticket = TicketFactory.getTicket(elements);
        ticket.print();
    }
}