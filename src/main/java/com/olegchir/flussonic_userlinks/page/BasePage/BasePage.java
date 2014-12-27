package com.olegchir.flussonic_userlinks.page.BasePage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.panels.NotificationErrorPanel.NotificationErrorPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

    public void displayErrorMessage(String componentId, String message, boolean showIt) {
        if (showIt) {
            add(new NotificationErrorPanel(componentId));
        } else {
            add(new EmptyPanel(componentId));
        }
    }

}