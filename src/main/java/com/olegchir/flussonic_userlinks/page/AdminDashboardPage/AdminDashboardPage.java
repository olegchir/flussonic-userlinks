package com.olegchir.flussonic_userlinks.page.AdminDashboardPage;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import com.olegchir.flussonic_userlinks.helpers.DB;
import com.olegchir.flussonic_userlinks.model.Test;
import com.olegchir.flussonic_userlinks.page.BodyPage.BodyPage;
import com.olegchir.flussonic_userlinks.panels.AdminDashboardPanel.AdminDashboardPanel;
import com.olegchir.flussonic_userlinks.panels.HomePanel.HomePanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_ADMIN")
public class AdminDashboardPage extends BodyPage {
    public AdminDashboardPage(PageParameters params) {
        super(params);
        //testDatabase();
    }

    private void testDatabase() {
        DB.executeInTransaction((em, tx) -> {
            Test test = em.find(Test.class, 1);
            if (test == null) {
                test = new Test();
                test.id = 1;
                test.data = "a";

                tx.begin();
                em.persist(test);
                tx.commit();
            }
            System.out.format("Test{id=%s, data=%s}\n", test.id, test.data);
        });
    }

    @Override
    public Panel getContentPanel() {
        return new AdminDashboardPanel(getNameContentPanel());
    }
}