package com.olegchir.flussonic_userlinks.panels.NotificationPanel;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.flussonic_userlinks.panels.BasePanel.BasePanel;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;


/**
 * Created by olegchir on 26.12.14.
 */
@SuppressWarnings("serial")
public class NotificationPanel extends BasePanel {
    public NotificationPanel(String id, String message, NotificationPanelType type) {
        super(id);

        //http://stackoverflow.com/questions/7922762/dynamic-html-attributes-in-wicket
        //https://cwiki.apache.org/confluence/display/WICKET/How+to+programatically+add+css+class+to+list+item
        //https://cwiki.apache.org/confluence/display/WICKET/How+to+modify+an+attribute+on+a+HTML+tag
        WebMarkupContainer messageWrapper = new WebMarkupContainer("messageWrapper");
        add(messageWrapper);
        messageWrapper.add(new AttributeAppender("class",new Model<String>(type.toString()), " "));
        messageWrapper.add(new Label("messageText", message));
    }
}
