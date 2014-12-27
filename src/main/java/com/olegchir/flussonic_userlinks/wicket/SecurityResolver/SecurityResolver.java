package com.olegchir.flussonic_userlinks.wicket.SecurityResolver;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import com.olegchir.flussonic_userlinks.auth.AuthChecker;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;

import java.util.Arrays;

/**
 * Created by olegchir on 28.12.14.
 */
public class SecurityResolver implements IComponentResolver
{
    private static final long serialVersionUID = -9164415653709912309L;

    public static final String TAGNAME_SECURITY = "security";

    // register the tagname
    static {
        WicketTagIdentifier.registerWellKnownTagName(TAGNAME_SECURITY);
    }

    @Override
    public Component resolve(final MarkupContainer container, final MarkupStream markupStream,
                             final ComponentTag tag)
    {
        // It must be <wicket:...>
        if (tag instanceof WicketTag)
        {
            final WicketTag wicketTag = (WicketTag)tag;

            // It must be <wicket:security...>
            if (TAGNAME_SECURITY.equalsIgnoreCase(tag.getName()))
            {
                boolean authorized = true;
                String rolesInOneString = StringUtils.trimToNull(wicketTag.getAttribute("onlyroles"));
                if (null!=rolesInOneString) {
                    Roles roles = AuthChecker.extractRoles();
                    authorized = roles.hasAnyRole(new Roles(rolesInOneString));
                }

                String id = wicketTag.getId() + container.getPage().getAutoIndex();

                Component result = new TransparentWebMarkupContainer(id);
                if (!authorized) {
                    result.setVisible(false);
                }

                return result;
            }
        }

        // We were not able to handle the componentId
        return null;
    }
}
