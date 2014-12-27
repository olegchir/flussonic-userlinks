package com.olegchir.flussonic_userlinks.page.AdminDashboardPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import com.olegchir.flussonic_userlinks.panels.AdminDashboardPanel.AdminDashboardPanel;
import com.olegchir.flussonic_userlinks.panels.HomePanel.HomePanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_ADMIN")
public class AdminDashboardPage extends BodyPage {
    public AdminDashboardPage(PageParameters params) {
        super(params);
    }

    @Override
    public Panel getContentPanel() {
        return new AdminDashboardPanel(getNameContentPanel());
    }
}