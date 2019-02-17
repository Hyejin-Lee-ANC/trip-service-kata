package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TripServiceTest {

    public static final User ANY_USER = null;
    public static final User LOGGED_IN_USER = new User();
    public static final User NOT_LOGGED_IN_USER = null;
    public static final Trip A_TRIP = new Trip();

    @Test(expected = UserNotLoggedInException.class)
    public void throws_an_exception_when_user_is_not_logged_in() {
        TripService service = new TripServiceFor(NOT_LOGGED_IN_USER);

        service.getTripsByUser(ANY_USER);
    }

    @Test
    public void returns_no_trips_for_user_with_no_friends() {
        TripService service = new TripServiceFor(LOGGED_IN_USER);
        User userWithNoFriends = new User();

        List<Trip> trips = service.getTripsByUser(userWithNoFriends);

        assertTrue(trips.isEmpty());
    }

    @Test
    public void no_trips_when_user_is_not_a_friend_of_the_logged_in_user() {
        TripService service = new TripServiceFor(LOGGED_IN_USER);

        User userWithFriends = new User();
        userWithFriends.addFriend(new User());

        List<Trip> trips = service.getTripsByUser(userWithFriends);

        assertTrue(trips.isEmpty());
    }

    @Test
    public void returns_the_trips_of_the_user_when_friends_of_the_logged_in_user() {
        TripService service = new TripServiceFor(LOGGED_IN_USER);

        User userWithFriends = new User();
        userWithFriends.addFriend(LOGGED_IN_USER);
        userWithFriends.addTrip(A_TRIP);

        List<Trip> trips = service.getTripsByUser(userWithFriends);

        assertTrue(trips.contains(A_TRIP));
    }

    // Override a class just for testing purposes

    private class TripServiceFor extends TripService {

        private User loggedInUser;

        public TripServiceFor(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        @Override
        protected List<Trip> tripsOf(User user) {
            return user.trips();
        }

        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }
    }
}