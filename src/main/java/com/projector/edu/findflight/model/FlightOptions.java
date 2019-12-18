package com.projector.edu.findflight.model;

import java.time.LocalDateTime;

public class FlightOptions {

    private String originSpot;

    private String destinationSpot;

    private int remainOriginDateTimeInMinutes;

    private int maxFlightRouteDurationInMinutes;

    private int maxPossibleTransfersNumber;

    private int maxWaitForNewFlightTimeInMinutes;

    private int minNeededTimeForTransferInMinutes;

    private int searchedFlightsLimitNumber;

    private LocalDateTime originDateTime;

    public String getOriginSpot() {
        return originSpot;
    }

    public void setOriginSpot(String originSpot) {
        this.originSpot = originSpot;
    }

    public String getDestinationSpot() {
        return destinationSpot;
    }

    public void setDestinationSpot(String destinationSpot) {
        this.destinationSpot = destinationSpot;
    }

    public int getMaxPossibleTransfersNumber() {
        return maxPossibleTransfersNumber;
    }

    public void setMaxPossibleTransfersNumber(int maxPossibleTransfersNumber) {
        this.maxPossibleTransfersNumber = maxPossibleTransfersNumber;
    }

    public int getSearchedFlightsLimitNumber() {
        return searchedFlightsLimitNumber;
    }

    public void setSearchedFlightsLimitNumber(int searchedFlightsLimitNumber) {
        this.searchedFlightsLimitNumber = searchedFlightsLimitNumber;
    }

    public int getMaxWaitForNewFlightTimeInMinutes() {
        return maxWaitForNewFlightTimeInMinutes;
    }

    public void setMaxWaitForNewFlightTimeInMinutes(int maxWaitForNewFlightTimeInMinutes) {
        this.maxWaitForNewFlightTimeInMinutes = maxWaitForNewFlightTimeInMinutes;
    }

    public int getRemainOriginDateTimeInMinutes() {
        return remainOriginDateTimeInMinutes;
    }

    public void setRemainOriginDateTimeInMinutes(int remainOriginDateTimeInMinutes) {
        this.remainOriginDateTimeInMinutes = remainOriginDateTimeInMinutes;
    }

    public int getMaxFlightRouteDurationInMinutes() {
        return maxFlightRouteDurationInMinutes;
    }

    public void setMaxFlightRouteDurationInMinutes(int maxFlightRouteDurationInMinutes) {
        this.maxFlightRouteDurationInMinutes = maxFlightRouteDurationInMinutes;
    }

    public int getMinNeededTimeForTransferInMinutes() {
        return minNeededTimeForTransferInMinutes;
    }

    public void setMinNeededTimeForTransferInMinutes(int minNeededTimeForTransferInMinutes) {
        this.minNeededTimeForTransferInMinutes = minNeededTimeForTransferInMinutes;
    }

    public LocalDateTime getOriginDateTime() {
        return originDateTime;
    }

    public void setOriginDateTime(LocalDateTime originDateTime) {
        this.originDateTime = originDateTime;
    }

}
