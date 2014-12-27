package com.olegchir.flussonic_userlinks.page.LoginPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import com.olegchir.flussonic_userlinks.page.BasePage.BasePage;
import com.olegchir.flussonic_userlinks.panels.NotificationPanel.NotificationPanelType;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;

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

        String authMessage = "";
        boolean authVisible = false;
        if (null!= lastException && AuthenticationException.class.isAssignableFrom(lastException.getClass())) {
            authMessage = ((AuthenticationException)lastException).getLocalizedMessage();
            authVisible = true;
        }
        addNotificationPanel("notification", authVisible, authMessage, NotificationPanelType.DANGER);
    }
}
