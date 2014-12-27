package com.olegchir.flussonic_userlinks.page.UsersPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.page.BasePage.BasePage;
import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_ADMIN")
public class UsersPage extends BodyPage {
    public UsersPage(PageParameters params) {
        super(params);
    }
}