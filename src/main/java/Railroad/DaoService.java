package Railroad;

import Service.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VBuevich on 11.09.2016.
 */
public class DaoService {

    public static List<Offer> getOffers(String depStation, String depTime, String arrStation, String arrTime) {

        List<Offer> offers = new ArrayList<Offer>();
        List<Schedule> listForDeparture = RailroadDao.scheduleForDeparture(depStation, depTime);
        List<Schedule> listForArrival = RailroadDao.scheduleForArrival(arrStation, arrTime);

        for (Schedule sD : listForDeparture) {
            for (Schedule sA : listForArrival) {
                if (sD.getTrainNumber().equals(sA.getTrainNumber())) {
                    long difference = sD.getTime().getTime() - sA.getTime().getTime();
                    if (difference < 0) {
                        Offer offer = new Offer();
                        offer.setArrivalStation(sA.getStationName());
                        offer.setArrivalTime(sA.getTime());
                        offer.setDepartureStation(sD.getStationName());
                        offer.setDepartureTime(sD.getTime());
                        offer.setTrainNumber(sD.getTrainNumber());
                        offers.add(offer);
                    }
                }
            }
        }

        return offers;
    }

    public static List<Passenger> getPassengerList(int tNumber){
        List<Passenger> passengerList = null;

        List<Ticket> tickets = TicketDao.getTicketsForTrainNumber(tNumber);

        if (tickets == null || tickets.isEmpty()){
            return passengerList;
        }
        passengerList = new ArrayList<Passenger>();
        for (Ticket t : tickets) {
            passengerList.add(PassengerDao.getPassenger(t.getPassengerId()));
        }

        return passengerList;
    }

}
