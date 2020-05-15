package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public static final ArrayList<Trip> NO_TRIPS = new ArrayList<>();

    private final UserSessions userSessions;
    private final TripsRepository tripsRepository;

    public TripService() {
        this(new UserSessions(), new TripsRepository());
    }

    public TripService(UserSessions userSessions, TripsRepository tripsRepository) {
        this.userSessions = userSessions;
        this.tripsRepository = tripsRepository;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        return getTripsByUser(user, userSessions.getLoggedInUser());
    }

    protected List<Trip> getTripsByUser(User user, User loggedUser) {
        checkIfTheUserIsLoggedIn(loggedUser);

        if (user.friendsOf(loggedUser)) {
            return tripsRepository.tripsOf(user);
        }

        return NO_TRIPS;
    }

    protected void checkIfTheUserIsLoggedIn(User loggedUser) {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
    }

}
