package com.olegchir.flussonic_userlinks.panels.BasePanel;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import java.lang.annotation.*;

/**
 * Created by olegchir on 28.12.14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Inherited
public @interface AuthorizeViewContentOnlyFor {
    /**
     * Gets the roles that are allowed to take the action.
     *
     * @return the roles that are allowed. Returns a zero length array by default
     */
    String[] value() default { };
}
