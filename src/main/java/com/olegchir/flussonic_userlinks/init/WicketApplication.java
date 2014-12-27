package com.olegchir.flussonic_userlinks.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.page.AdminDashboardPage.AdminDashboardPage;
import com.olegchir.flussonic_userlinks.page.DashboardPage.DashboardPage;
import com.olegchir.flussonic_userlinks.page.HomePage.HomePage;
import com.olegchir.flussonic_userlinks.page.LoginPage.LoginFailedPage;
import com.olegchir.flussonic_userlinks.page.LoginPage.LoginPage;
import com.olegchir.flussonic_userlinks.page.LogoutPage.LogoutSuccessPage;
import com.olegchir.flussonic_userlinks.page.UserManagementPage.UserManagementPage;
import com.olegchir.flussonic_userlinks.wicket.IncludeResolver.IncludeResolver;
import com.olegchir.flussonic_userlinks.wicket.SecurityResolver.SecurityResolver;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.settings.PageSettings;
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

        //Strip annoying Wicket markup, even in development mode
        //It's REQUIRED for correct operation of CSS styles in UI
        //https://cwiki.apache.org/confluence/display/WICKET/How+to+remove+wicket+markup+from+output
        getMarkupSettings().setStripWicketTags(true);

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
        usePage("/login", LoginPage.class);
        usePage("/login_failed", LoginFailedPage.class);
        usePage("/logout_success", LogoutSuccessPage.class);
        usePage("/home", HomePage.class);
        usePage("/admin_dashboard", AdminDashboardPage.class);
        usePage("/dashboard", DashboardPage.class);
        usePage("/user_management", UserManagementPage.class);

        PageSettings pageSettings = getPageSettings();
        pageSettings.addComponentResolver(new IncludeResolver());
        pageSettings.addComponentResolver(new SecurityResolver());
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