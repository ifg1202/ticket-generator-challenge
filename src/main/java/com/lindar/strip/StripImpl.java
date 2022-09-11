package com.lindar.strip;

import com.lindar.utils.RandomEngine;
import com.lindar.ticket.Ticket;
import com.lindar.ticket.TicketFactory;
import com.lindar.ticket.TicketUtils;

import java.util.*;

final public class StripImpl implements Strip {

    private static final String TICKET_SEPARATOR = "----------------------------";
    public static final int LAST_TICKET_ELEMENT = 14;
    public static final int FIRST_ELEMENT = 0;

    RandomEngine randomEngine;
    private final Ticket[] tickets = new Ticket[TOTAL_TICKETS];

    public StripImpl(RandomEngine randomEngine) {
        this.randomEngine = randomEngine;
    }

    public void print() {
        for (Ticket ticket : tickets) {
            ticket.print();
            System.out.println(TICKET_SEPARATOR);
        }
    }

    void create() {
        int[][] elementsForTickets = getElementsForTickets();
        for (int ticket = 0; ticket < TOTAL_TICKETS; ticket++) {
            tickets[ticket] = TicketFactory.getTicket(elementsForTickets[ticket]);
        }
    }

    int[][] getElementsForTickets() {
        int[][] elementsForTickets = new int[Strip.TOTAL_TICKETS][Ticket.TOTAL_ELEMENTS];
        Map<Integer, List<Integer>> stripRandomNumbers = randomEngine.getStripRandomNumberMap(Ticket.TOTAL_COLUMNS);
        addOneElementToEachColumn(elementsForTickets, stripRandomNumbers);
        addRestElementsToEachTicket(elementsForTickets, stripRandomNumbers);
        return elementsForTickets;
    }

    void addOneElementToEachColumn(int[][] elementsForTickets, Map<Integer, List<Integer>> stripRandomNumbers) {
        for (int ticket = 0; ticket < Strip.TOTAL_TICKETS; ticket++) {
            for (int column = 0; column < Ticket.TOTAL_COLUMNS; column++) {
                elementsForTickets[ticket][column] = stripRandomNumbers.get(column).remove(FIRST_ELEMENT);
            }
        }
    }

    private void addRestElementsToEachTicket(int[][] elementsForTickets, Map<Integer, List<Integer>> stripRandomNumbers) {
        List<Integer> candidates =new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8));
        for (int ticket = 0; ticket < Strip.TOTAL_TICKETS; ticket++) {
            for (int rest = Ticket.TOTAL_COLUMNS; rest < Ticket.TOTAL_ELEMENTS; rest++) {
                int[] commons = getCommonElementsFromArrays(getInsertableColumns(elementsForTickets[ticket]), candidates.stream().mapToInt(i->i).toArray());
                if(commons.length != 0) {
                    int column = randomEngine.getAnyFromCollection(commons);
                    elementsForTickets[ticket][rest] = stripRandomNumbers.get(column).remove(FIRST_ELEMENT);
                    if (stripRandomNumbers.get(column).isEmpty()) {
                        candidates.remove((Integer) column);
                    }
                } else {
                    switching(elementsForTickets[ticket], elementsForTickets, candidates, stripRandomNumbers, rest);
                }
            }
        }
    }

    private void switching(int[] ticket, int[][] elementsForTickets, List<Integer> candidates, Map<Integer, List<Integer>> stripRandomNumbers, int rest) {
        List<Integer> ticketsCandidates = new ArrayList<>(Arrays.asList(0,1,2,3,4));
        for (int ticketSwitchable : ticketsCandidates) {
            if(isInsertableInTheColumn(ticket, elementsForTickets[ticketSwitchable][LAST_TICKET_ELEMENT])) {
                int[] commons = getCommonElementsFromArrays(getInsertableColumns(elementsForTickets[ticketSwitchable]), candidates.stream().mapToInt(i->i).toArray());
                if (commons.length != 0) {
                    ticket[rest] = elementsForTickets[ticketSwitchable][LAST_TICKET_ELEMENT];
                    int elsi= randomEngine.getAnyFromCollection(commons);
                    elementsForTickets[ticketSwitchable][LAST_TICKET_ELEMENT] = stripRandomNumbers.get(elsi).remove(FIRST_ELEMENT);
                    if (stripRandomNumbers.get(elsi).isEmpty()) {
                        candidates.remove((Integer) elsi);
                    }
                    break;
                }
            }
        }
    }

    boolean isInsertableInTheColumn(int[] ticket, int elementTicketSwitchable) {
        int counter = 0;
        int columnOfTheElementTicketSwitchable = TicketUtils.getColumnBasedOnElement(elementTicketSwitchable);
        for (int element : ticket) {
            int columnOfTheElement = TicketUtils.getColumnBasedOnElement(element);
            if (columnOfTheElement == columnOfTheElementTicketSwitchable)
                counter++;
        }
        return counter < Ticket.TOTAL_ROWS;
    }

    int getSwitcheableValue(int[] elements) {
        Map<Integer, Integer> insertableElementsMap = getEmptyColumnsMap();
        for (int element: elements) {
            if ( element != 0) {
                int column = TicketUtils.getColumnBasedOnElement(element);
                int count = insertableElementsMap.get(column);
                insertableElementsMap.put(column, count + 1);
            }
        }
        List<Integer> removable = new ArrayList<>();
        for (int i = 0; i < Ticket.TOTAL_COLUMNS; i++) {
            if (insertableElementsMap.get(i) > 1)
                removable.add(i);
        }
        return randomEngine.getAnyFromCollection(removable.stream().mapToInt(i->i).toArray());
    }


    int[] getInsertableColumns(int[] elements) {
        Map<Integer, Integer> insertableElementsMap = getEmptyColumnsMap();
        for (int element: elements) {
            if (element != Ticket.EMPTY_CELL) {
                int column = TicketUtils.getColumnBasedOnElement(element);
                int count = insertableElementsMap.get(column);
                insertableElementsMap.put(column, count + 1);
            }
        }
        List<Integer> insertables = new ArrayList<>();
        for (int column = 0; column < Ticket.TOTAL_COLUMNS; column++) {
            if (insertableElementsMap.get(column) < Ticket.TOTAL_ROWS)
                insertables.add(column);
        }
        return insertables.stream().mapToInt(i->i).toArray();
    }

    Map<Integer, Integer> getEmptyColumnsMap() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int column = 0; column < Ticket.TOTAL_COLUMNS; column++) {
            map.put(column, Ticket.EMPTY_CELL);
        }
        return map;
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
}
