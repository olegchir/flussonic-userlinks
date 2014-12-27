package com.olegchir.flussonic_userlinks.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.page.AdminDashboardPage.AdminDashboardPage;
import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import com.olegchir.flussonic_userlinks.page.HomePage.HomePage;
import com.olegchir.flussonic_userlinks.page.LoginPage.LoginFailedPage;
import com.olegchir.flussonic_userlinks.page.LoginPage.LoginPage;
import com.olegchir.flussonic_userlinks.page.LogoutPage.LogoutSuccessPage;
import com.olegchir.flussonic_userlinks.page.UsersPage.UsersPage;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
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
        usePage("/login", LoginPage.class);
        usePage("/login_failed", LoginFailedPage.class);
        usePage("/logout_success", LogoutSuccessPage.class);
        usePage("/home", HomePage.class);
        usePage("/admin_dashboard", AdminDashboardPage.class);
        usePage("/users", UsersPage.class);
    }

    public <T extends Page> void usePage(String path, final Class<T> pageClass) {
        mountPage(path, pageClass);
        MountedPages.getInstance().add(path, pageClass);
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