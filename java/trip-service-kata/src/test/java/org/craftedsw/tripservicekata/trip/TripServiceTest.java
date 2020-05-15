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
    public static final User USER_WITH_NO_FRIENDS = new User();

    private FakeTripsRepository tripsRepository = new FakeTripsRepository();

    @Test(expected = UserNotLoggedInException.class)
    public void throws_an_exception_when_user_is_not_logged_in() {
        UserSessions userSessions = new UserSessionsThatReturn(NOT_LOGGED_IN_USER);
        TripService service = new TripService(userSessions, tripsRepository);

        service.getTripsByUser(ANY_USER);
    }

    @Test
    public void returns_no_trips_for_user_with_no_friends() {
        UserSessions userSessions = new UserSessionsThatReturn(LOGGED_IN_USER);
        TripService service = new TripService(userSessions, tripsRepository);

        List<Trip> trips = service.getTripsByUser(USER_WITH_NO_FRIENDS);

        assertTrue(trips.isEmpty());
    }

    @Test
    public void no_trips_when_user_is_not_a_friend_of_the_logged_in_user() {
        UserSessions userSessions = new UserSessionsThatReturn(LOGGED_IN_USER);
        TripService service = new TripService(userSessions, tripsRepository);

        List<Trip> trips = service.getTripsByUser(userWithFriends());

        assertTrue(trips.isEmpty());
    }

    @Test
    public void returns_the_trips_of_the_user_when_friends_of_the_logged_in_user() {
        UserSessions userSessions = new UserSessionsThatReturn(LOGGED_IN_USER);
        TripService service = new TripService(userSessions, tripsRepository);

        User userWithFriends = new User();
        userWithFriends.addFriend(LOGGED_IN_USER);
        userWithFriends.addTrip(A_TRIP);

        List<Trip> trips = service.getTripsByUser(userWithFriends);

        assertTrue(trips.contains(A_TRIP));
    }

    private class UserSessionsThatReturn extends UserSessions {
        private User loggedInUser;

        public UserSessionsThatReturn(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }

    }

    private class FakeTripsRepository extends TripsRepository {
        @Override
        protected List<Trip> tripsOf(User user) {
            return user.trips();
        }
    }

    private User userWithFriends() {
        User userWithFriends = new User();
        userWithFriends.addFriend(new User());
        return userWithFriends;
    }
}