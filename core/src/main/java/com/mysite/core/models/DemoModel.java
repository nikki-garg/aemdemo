package com.mysite.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;


@Model(adaptables = SlingHttpServletRequest.class , defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class DemoModel {

    private Logger logger = LoggerFactory.getLogger(DemoModel.class);

    @Inject
    @Via("resource")
    @Named("title")
    @Default(values = "no heading authored")
    private String heading;

    @ValueMapValue(name = "link")
    @Default(values = "no link authored")
    private String linkUrl;

    @OSGiService
    private ResourceResolverFactory factory;

    @Inject
    private SlingSettingsService slingSettingsService;

    @SlingObject
    private ResourceResolver resolver;

    private String tag;

    @PostConstruct
    public void sayHello() {
        logger.debug("Inside sayHello");
        logger.info("Nikita {} linkurl {}",slingSettingsService.getRunModes(),linkUrl);
        logger.debug("factory123 "+factory + linkUrl+ "");
    }


    public long getListOfResource(){
long count = 0;
            TagManager tagMgr = resolver.adaptTo(TagManager.class);
            if(tagMgr!=null){
                Tag tag = tagMgr.resolve("mysite:clothing/shirts");
              count =  tag.getCount();
            }

return count;
    }

    public String getHeading() {
        return heading;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getName() throws JSONException {
        JSONArray array = new JSONArray();
        array.put("Nikita");
        return (String) array.get(0);
    }
}
