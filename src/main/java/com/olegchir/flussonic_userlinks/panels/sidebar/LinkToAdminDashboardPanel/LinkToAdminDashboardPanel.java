package com.olegchir.flussonic_userlinks.panels.sidebar.LinkToAdminDashboardPanel;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.auth.AuthorizeViewContentOnlyFor;
import com.olegchir.flussonic_userlinks.panels.BasePanel.BasePanel;


/**
 * Created by olegchir on 26.12.14.
 */
@AuthorizeViewContentOnlyFor("ROLE_ADMIN")
public class LinkToAdminDashboardPanel extends BasePanel {
    public LinkToAdminDashboardPanel(String id) {
        super(id);
    }
}
