package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class UserSessions {
    public UserSessions() {
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}