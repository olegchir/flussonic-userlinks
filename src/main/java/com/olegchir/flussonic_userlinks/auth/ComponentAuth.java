package com.olegchir.flussonic_userlinks.auth;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import com.olegchir.flussonic_userlinks.panels.EmptyPanel.ULEmptyPanel;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by olegchir on 28.12.14.
 */
public class ComponentAuth {
    public static Component protect(Component component) {
        return component;
    }

    public static Component protect(Panel panel) {
        Roles roles = AuthChecker.extractRoles();
        boolean authorized = true;

        Class clazz = panel.getClass();
        AuthorizeViewContentOnlyFor classAnnotation = (AuthorizeViewContentOnlyFor)clazz.getAnnotation(AuthorizeViewContentOnlyFor.class);
        if (classAnnotation != null) {
            authorized = roles.hasAnyRole(new Roles(classAnnotation.value()));
        }

        return authorized ? panel :  new ULEmptyPanel(panel.getId());
    }
}
