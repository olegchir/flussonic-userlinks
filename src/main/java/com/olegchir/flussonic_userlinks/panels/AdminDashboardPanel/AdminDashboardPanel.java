package com.olegchir.flussonic_userlinks.panels.AdminDashboardPanel;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.auth.AuthorizeViewContentOnlyFor;
import com.olegchir.flussonic_userlinks.panels.BasePanel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;


/**
 * Created by olegchir on 26.12.14.
 */
@AuthorizeViewContentOnlyFor("ROLE_ADMIN")
public class AdminDashboardPanel extends BasePanel {
    public AdminDashboardPanel(String id) {
        super(id);
        add(new Label("replaceme", new Model<String>("some replaced text")));
    }
}
