package com.olegchir.flussonic_userlinks.page.BodyPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.page.BasePage.BasePage;
import com.olegchir.flussonic_userlinks.panels.EmptyPanel.ULEmptyPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_USER")
abstract public class BodyPage extends BasePage {
    public static final String NAME_CONTENT_PANEL = "contentPanel";

    public BodyPage(PageParameters params) {
        super(params);
        add(getContentPanel());
    }

    public Panel getContentPanel() {
        return new ULEmptyPanel(getNameContentPanel());
    }

    public String getNameContentPanel() {
        return NAME_CONTENT_PANEL;
    }
}