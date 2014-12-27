package com.olegchir.flussonic_userlinks.wicket.IncludeResolver;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.response.StringResponse;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolver of wicket:include tag
 *
 * Originally from: http://sanityresort.blogspot.ru/2011/08/creating-custom-wicket-tag-resolver.html
 * Adopted by: Oleg Chirukhin
 *
 * This class is a tag resolver to handle tags of the following format
 * <wicket:include [key="xxx"|filename="xxx"]/>.
 *
 *
 * This class tries to provide the same functionality for included html files as
 * WicketMessageResolver does for property file entries but also tries to avoid
 * to fail early.
 *
 * You have to specify either a key or a filename. A key will be looked up via
 * the resource mechanism and the result will be used as filename.
 * In addition you can nest tags within this tag. Those child tags will then be
 * used to generate the replacements for variables defined in the file content.
 * If this fails for a variable the parent's model object will queried for a
 * property with the variable's value as name. Should this also fail the last
 * fall back is the same query on the parent object.
 *
 * If the resource settings are set to throw exceptions on missing properties
 * this class will raise an exception if either a variable cannot be looked up
 * or a child component is not replaced.
 *
 * This tag allows you to easily include files in your pages which can also
 * depend on your localized properties.
 *
 * Usage:
 *     <wicket:include key="myFile">
 *        This text will be replaced with text from the specified file.
 *        <span wicket:id="replaceme">[to be replaced]</span>.
 *     </wicket:include>
 *
 *
 * Then your property file needs an entry like this:
 *
 *
 * myFile = path/to/file
 *
 *
 * This file contains html fragments with variables:
 *
 *
 * This is a file with ${replaceme}.
 *
 *
 * And your java component add:
 *
 *
 * add(new Label("replaceme", new Model<String>("some replaced text")));
 *
 *
 * The output will be:
 *
 *
 * This is a file with some replaced text.
 *
 *
 * @author Chris
 *
 */
public class IncludeResolver implements IComponentResolver {

    /**
     *
     */
    private static final long serialVersionUID = -9164415653709941809L;

    private static final String tagname = "include";

    private static final Logger log = LoggerFactory.getLogger(IncludeResolver.class);

    // register the tagname
    static {
        WicketTagIdentifier.registerWellKnownTagName(tagname);
    }

    /**
     * Handles resolving the wicket:include tag. If a filename is specified this
     * file will be looked up. If a key is specified it's value is looked up
     * using the resource mechanism. The looked up value will then be used as
     * filename.
     *
     */
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {

        if ((tag instanceof WicketTag) && tagname.equalsIgnoreCase(tag.getName())) {
            WicketTag wtag = (WicketTag) tag;

            String fileName = StringUtils.trimToNull(wtag.getAttribute("file"));
            String fileKey = StringUtils.trimToNull(wtag.getAttribute("key"));

            if (null != fileKey) {
                if (null != fileName) {
                    throw new MarkupException( "Wrong format of: you must not use file and key attribtue at once");
                }
                fileName = StringUtils.trimToNull(container.getString(fileKey));
                if (null == fileName) {
                    throw new MarkupException("The key inside could not be resolved");
                }

            } else if (null == fileName) {
                throw new MarkupException(
                        "Wrong format of: specify the file or key attribute");
            }

            final String id = "_" + tagname + "_" + container.getPage().getAutoIndex();
            IncludeContainer ic = new IncludeContainer(id, fileName, fileKey, markupStream);
            ic.setRenderBodyOnly(container.getApplication().getMarkupSettings().getStripWicketTags());
//            container.autoAdd(ic, markupStream);

            return ic;

        }
        return null;
    }

    /**
     * Helper class to break open the original Include class by using
     * inheritance.
     *
     * @author Chris
     *
     */
    private static final class ExposingInclude extends Include {

        /**
         *
         */
        private static final long serialVersionUID = -212861726226482365L;

        public ExposingInclude(String id, String filename) {
            super(id, filename);
        }

        public final String expose() {
            return this.importAsString();
        }

    }

    /**
     * Container class to render the included file.
     *
     * @author Chris
     *
     */
    private static final class IncludeContainer extends MarkupContainer {

        /**
         *
         */
        private static final long serialVersionUID = 3991477945186422264L;
        private final String key;
        private final String fileName;

        public IncludeContainer(String id, String fileName, String key, MarkupStream markupStream) {
            super(id, new Model(fileName));
            this.key = key;
            this.fileName = fileName;

        }

        /**
         * Wrapper to log this instance's settings.
         *
         * @return String containing the instance's settings.
         */
        private final String logParams() {
            return " tag with " + (null != this.key ? "key = '" + this.key + "' and resulting " : "") + "fileName = '" + this.fileName + "'";
        }

        /**
         * Renders the component tag. The base content is read from the file
         * defined by the filename. Afterwards all containing variables are
         * replaced by the following methods in this order:
         *

         *
         trying to get the value from a child component that has the
         * variable's value as id
         *
         trying to get the value as property from the parent's model
         * object
         *
         trying to get the value as property from the parent object
         *

         *
         * If all attempts to lookup up variable fail or not all child tags are
         * replaced either an exception is thrown or a warning is logged
         * depending on the resource settings.
         *
         */
        @Override
        public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {

            // get all child tags' rendered contents
            Map children = this.getRenderedChildren(markupStream, openTag);

            // read the included file content
            String fileContent = new ExposingInclude(this.getId() + "_internalInclude", this.getDefaultModelObjectAsString()).expose();

            // remember all replaced child tags
            final Set replacedChildren = new HashSet();

            // substitute all variables and set the result as response
            this.getResponse().write(new MapVariableInterpolator(fileContent, children) {

                @Override
                protected String getValue(String variableName) {

                    // try to get value from rendered child tags
                    String value = super.getValue(variableName);

                    if (null != value) {
                        // if we used a child tag create a marker
                        replacedChildren.add(variableName);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug(IncludeContainer.this.logParams()
                                    + " - Could not find replacement value for variable = '" + variableName
                                    + "' in child tags");
                        }
                        // see if the variable can be replaced using the
                        // parent's model object - use try/catch to avoid fail
                        // early
                        try {
                            value = Strings.toString(PropertyResolver.getValue(variableName, IncludeContainer.this.getParent().getDefaultModelObject()));
                        } catch (WicketRuntimeException e) {
                            if (log.isDebugEnabled()) {
                                log.debug(IncludeContainer.this.logParams()
                                        + " - Could not find replacement value for variable = '" + variableName
                                        + "' in parent model");
                            }
                        }
                    }

                    if (null == value) {
                        // see if the variable can be replaced using the parent
                        // object - use try/catch to avoid fail early
                        try {
                            value = Strings.toString(PropertyResolver.getValue(variableName, IncludeContainer.this.getParent()));
                        } catch (WicketRuntimeException e) {
                            if (log.isDebugEnabled()) {
                                log.debug(IncludeContainer.this.logParams()
                                        + " - Could not find replacement value for variable = '" + variableName
                                        + "' in parent object");
                            }
                        }
                    }

                    if (null == value) {
                        // Handle failed lookup
                        String logMsg = IncludeContainer.this.logParams()
                                + " - Bailing with exception since variable = '" + variableName
                                + "' could not be replaced";

                        if (IncludeContainer.this.isThrowingExceptions()) {
                            if (log.isDebugEnabled()) {
                                log.debug(logMsg);

                            }
                            markupStream.throwMarkupException(IncludeContainer.this.logParams()
                                    + " - Could not replace variable  = '" + variableName + "'");

                        } else {
                            log.warn(logMsg);
                        }
                    }

                    return value;
                }

            }.toString());

            // Make sure all of the children were rendered
            Iterator<String> iter = children.keySet().iterator();
            while (iter.hasNext()) {
                String id = iter.next();
                if (replacedChildren.contains(id) == false) {
                    String msg = "The  for file " + this.fileName
                            + "has a child element with wicket:id=\"" + id + "\". You must add the variable ${" + id
                            + "} to the file content for the wicket:include.";

                    if (this.isThrowingExceptions() == true) {
                        markupStream.throwMarkupException(msg);
                    } else {
                        log.warn(msg);
                    }
                }
            }
        }

        /**
         * Wraps call to resource settings to determine if a exception should be
         * thrown in case not all variables are resolved or not all children are
         * rendered.
         *
         * @return
         *         Application.get().getResourceSettings().getThrowExceptionOnMissingResource()
         */
        private final boolean isThrowingExceptions() {
            return Application.get().getResourceSettings().getThrowExceptionOnMissingResource();
        }

        /**
         * Walks through all children of the current tag and stores their
         * rendering results in a map.
         *
         * @param markupStream
         *            The markupStream associated with the tag.
         * @param openTag
         *            The current include tag to be rendered.
         * @return Map containing all children's rendered responses associated
         *         with each child's id.
         */
        private Map<String, String> getRenderedChildren(final MarkupStream markupStream, final ComponentTag openTag) {
            Map<String, String> children = new HashMap<>();

            if (!openTag.isOpenClose()) {

                while (markupStream.hasMore() && !markupStream.get().closes(openTag)) {
                    MarkupElement element = markupStream.get();
                    // If it a tag like  or
                    if ((element instanceof ComponentTag) && !markupStream.atCloseTag()) {
                        String id = ((ComponentTag) element).getId();

                        Component comp = this.getParent().get(id);
                        if (comp != null) {
                            children.put(id, this.getRenderedResponseString(markupStream, comp));
                            markupStream.next();
                        } else {
                            markupStream.next();
                        }
                    } else {
                        markupStream.next();
                    }
                }

            }

            return children;
        }

        /**
         * Obtains the rendered response of the given component rendered
         * according to the markupStream. Rendering uses a temporary response
         * and the original response is restored before returning the result.
         *
         * @param markupStream
         *            The markupStream used to render the component.
         * @param comp
         *            The component to be rendered.
         * @return The rendered result.
         */
        private String getRenderedResponseString(final MarkupStream markupStream, Component comp) {
            Response webResponse = comp.getResponse();

            StringResponse response = new StringResponse();

            try {
                this.getRequestCycle().setResponse(response);
                comp.setMarkup(markupStream.getMarkupFragment());
                comp.render();
            } finally {
                this.getRequestCycle().setResponse(webResponse);
            }
            return response.getBuffer().toString();
        }
    }
}