package com.mysite.core.wcmuse;

import com.adobe.cq.sightly.WCMUsePojo;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoUseClass extends WCMUsePojo {

    private Logger logger = LoggerFactory.getLogger(DemoUseClass.class);

    private String title , redirectTarget , description;

    private int depth;
    private boolean disabled;
    private boolean preview;
    private String path ;

    @Override
    public void activate() throws Exception {
        ValueMap properties = getProperties();
        logger.debug("inside activate");
        this.title = properties.get("title",String.class);
        this.depth= getCurrentPage().getDepth();
        this.redirectTarget =  getInheritedPageProperties().get("redirectTarget",String.class);
        this.description = getInheritedPageProperties().get("jcr:description",String.class);
       this.disabled = getWcmMode().isDisabled();
        this.preview =getWcmMode().isPreview();

       this.path = getCurrentDesign().getPath();


    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getRedirectTarget() {
        return redirectTarget;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isPreview() {
        return preview;
    }
}
