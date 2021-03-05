package com.mysite.core.listeners;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component(immediate = true, service = EventHandler.class, property = {
        Constants.SERVICE_DESCRIPTION + "= This event handler listens the events on page activation",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
        EventConstants.EVENT_FILTER + "(&" + "(path=/content/mysite/us/en/*/jcr:content) (|("
                + SlingConstants.PROPERTY_CHANGED_ATTRIBUTES + "=*jcr:title) " + "(" + ResourceChangeListener.CHANGES
                + "=*jcr:title)))" })

@ServiceDescription("Demo to Show on changes in the resource tree")
public class BasicEventHandler implements EventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleEvent(Event event) {
        logger.debug("Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
    }
}
