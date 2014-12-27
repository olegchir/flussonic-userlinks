package com.olegchir.flussonic_userlinks.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


/**
 * Created by olegchir on 25.12.14.
 */
import com.olegchir.flussonic_userlinks.auth.AuthChecker;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

@SuppressWarnings("serial")
public class UserAuthenticatedWebSession extends AuthenticatedWebSession {
    public UserAuthenticatedWebSession(Request request) {
        super(request);
    }
    @Override
    public boolean authenticate(String username, String password) {
        throw new UnsupportedOperationException("You are supposed to use Spring-Security!");
    }
    @Override
    public Roles getRoles() {
        return AuthChecker.extractRoles();
    }
}