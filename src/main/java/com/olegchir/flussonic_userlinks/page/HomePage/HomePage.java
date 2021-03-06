package com.olegchir.flussonic_userlinks.page.HomePage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.page.AdminDashboardPage.AdminDashboardPage;
import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import com.olegchir.flussonic_userlinks.panels.HomePanel.HomePanel;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_USER")
public class HomePage extends BodyPage {
    public HomePage(PageParameters params) {
        super(params);
        //http://stackoverflow.com/questions/3334827/wicket-how-to-redirect-to-another-page
        throw new RedirectToUrlException("admin_dashboard");
    }

    @Override
    public Panel getContentPanel() {
        return new HomePanel(getNameContentPanel());
    }
}