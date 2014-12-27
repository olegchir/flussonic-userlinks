package com.olegchir.flussonic_userlinks.panels.NotificationPanel;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


/**
 * Created by olegchir on 27.12.14.
 */
public enum NotificationPanelType {
    SUCCESS("alert-success"),
    INFO("alert-info"),
    WARNING("alert-warning"),
    DANGER("alert-danger");

    private final String text;

    /**
     * @param text
     */
    private NotificationPanelType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
