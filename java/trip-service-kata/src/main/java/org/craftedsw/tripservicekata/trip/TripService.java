package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public static final ArrayList<Trip> NO_TRIPS = new ArrayList<>();

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        return getTripsByUser(user, getLoggedInUser());
    }

    protected List<Trip> getTripsByUser(User user, User loggedUser) {
        checkIfTheUserIsLoggedIn(loggedUser);

        if (user.friendsOf(loggedUser)) {
            return tripsOf(user);
        }

        return NO_TRIPS;
    }

    protected void checkIfTheUserIsLoggedIn(User loggedUser) {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    // Seams

    protected List<Trip> tripsOf(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
