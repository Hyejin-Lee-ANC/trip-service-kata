package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripsRepository {
    public TripsRepository() {
    }

    protected List<Trip> tripsOf(User user) {
        return TripDAO.findTripsByUser(user);
    }
}