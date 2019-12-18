package com.projector.edu.findflight.business.searchers;

import com.projector.edu.findflight.model.FlightRoute;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class BestFlightRoutesHolder {

    private PriorityQueue<FlightRoute> increasingPriceFlightsHeap;

    private PriorityQueue<FlightRoute> decreasingPriceFlightsHeap;

    private int maxRouteNumber;

    private int currentRouteNumber;

    public BestFlightRoutesHolder(int maxRouteNumber) {
        this.increasingPriceFlightsHeap = new PriorityQueue<>((FlightRoute f1, FlightRoute f2) -> f1.getPrice() - f2.getPrice());
        this.decreasingPriceFlightsHeap = new PriorityQueue<>((FlightRoute f1, FlightRoute f2) -> f2.getPrice() - f1.getPrice());
        this.maxRouteNumber = maxRouteNumber;
    }

    public void addFlightRoute(FlightRoute flightRoute) {
        if (currentRouteNumber < maxRouteNumber) {
            increasingPriceFlightsHeap.add(flightRoute);
            decreasingPriceFlightsHeap.add(flightRoute);
            currentRouteNumber++;
        } else {
            FlightRoute biggestPriceRoute = decreasingPriceFlightsHeap.peek();
            if (flightRoute.getPrice() < biggestPriceRoute.getPrice()) {
                decreasingPriceFlightsHeap.poll();
                increasingPriceFlightsHeap.remove(biggestPriceRoute);
                decreasingPriceFlightsHeap.add(flightRoute);
                increasingPriceFlightsHeap.add(flightRoute);
            }
        }
    }

    public int getMaxPrice() {
        return decreasingPriceFlightsHeap.peek().getPrice();
    }

    public int getCurrentRouteNumber() {
        return currentRouteNumber;
    }

    public List<FlightRoute> getSortedFlightRoutes() {
        List<FlightRoute> sortedFlightRoutes = new LinkedList<>();
        Iterator<FlightRoute> flightRouteIterator = increasingPriceFlightsHeap.iterator();
        while (flightRouteIterator.hasNext()) {
            sortedFlightRoutes.add(increasingPriceFlightsHeap.poll());
        }
        return sortedFlightRoutes;
    }

}
