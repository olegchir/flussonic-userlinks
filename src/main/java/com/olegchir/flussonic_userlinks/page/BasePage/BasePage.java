package com.olegchir.flussonic_userlinks.page.BasePage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.auth.ComponentAuth;
import com.olegchir.flussonic_userlinks.panels.EmptyPanel.ULEmptyPanel;
import com.olegchir.flussonic_userlinks.panels.NotificationPanel.NotificationPanel;
import com.olegchir.flussonic_userlinks.panels.NotificationPanel.NotificationPanelType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by olegchir on 24.12.14.
 */

public abstract class BasePage extends WebPage {
    public BasePage(PageParameters params) {
        super();
        add(new Label("pageTitle", new StringResourceModel("page.title", this, null)));
    }
    public void setPageTitleByModel(IModel model) {
        get("pageTitle").setDefaultModel(model);
    }
    public void setPageTitle(String pageTitle) {
        setPageTitleByModel(new StringResourceModel(pageTitle, this, null));
    }

    public void addNotificationPanel(String componentId, boolean visible, String message, NotificationPanelType type) {
        if (visible) {
            add(new NotificationPanel(componentId, message, type));
        } else {
            add(new ULEmptyPanel(componentId));
        }
    }

    public void add(Panel panel) {
        add(ComponentAuth.protect(panel));
    }

}