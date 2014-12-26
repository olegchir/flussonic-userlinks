package com.olegchir.flussonic_userlinks.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import com.olegchir.flussonic_userlinks.page.HomePage.HomePage;
import com.olegchir.flussonic_userlinks.page.LoginPage.LoginPage;
import com.olegchir.flussonic_userlinks.page.LogoutPage.LogoutSuccessPage;
import com.olegchir.flussonic_userlinks.page.UsersPage.UsersPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by olegchir on 24.12.14.
 */
public class WicketApplication extends AuthenticatedWebApplication  {
    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
        mountPage("/login", LoginPage.class);
        mountPage("/logout_success", LogoutSuccessPage.class);
        mountPage("/home", HomePage.class);
        mountPage("/users", UsersPage.class);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return UserAuthenticatedWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }
}