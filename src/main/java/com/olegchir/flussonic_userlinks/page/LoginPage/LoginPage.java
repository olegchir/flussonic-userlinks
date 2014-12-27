package com.olegchir.flussonic_userlinks.page.LoginPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import com.olegchir.flussonic_userlinks.page.BasePage.BasePage;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

import javax.security.auth.login.CredentialException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.util.WebUtils;

/**
 * Created by olegchir on 25.12.14.
 */
public class LoginPage extends BasePage {
    public LoginPage(PageParameters params) {
        super(params);

        //http://stackoverflow.com/questions/1373407/how-to-display-custom-error-message-in-jsp-for-spring-security-auth-exception
        Request request = RequestCycle.get().getRequest();
        Object lastException = WebUtils.getSessionAttribute(
                ((ServletWebRequest) request).getContainerRequest(),
                WebAttributes.AUTHENTICATION_EXCEPTION
        );

        if (null!= lastException && AuthenticationException.class.isAssignableFrom(lastException.getClass())) {
            String message = ((AuthenticationException)lastException).getLocalizedMessage();
            displayErrorMessage("notification", message, true);
        } else {
            displayErrorMessage("notification", "Login error", false);
        }
    }
}
