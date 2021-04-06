package com.mysite.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TagModel {

    private Logger logger = LoggerFactory.getLogger(TagModel.class);

    @ValueMapValue
    private String heading;

    @ValueMapValue(name = "tag")
    private String[] tags;

    @SlingObject
    ResourceResolver resolver;

    public List<String> getTagOnPages(){
        List<String> pageNameList = new ArrayList<String>();

        TagManager tagMgr = resolver.adaptTo(TagManager.class);


        for(String stringTag:tags){
            Tag tag =  tagMgr.resolve(stringTag);

            Iterator<Resource> resourceIterator = tag.find();

            while(resourceIterator.hasNext()){
                Resource resource = resourceIterator.next();
                logger.debug("Resource Path {}",resource.getPath());
                // Adapt it to page
                Page page = resource.getParent().adaptTo(Page.class);
                if(null!=page){
                    String pageName = page.getName();

                    pageNameList.add(pageName);
                }
               // pageNameList.add(resource.getPath());
            }


        }
        return pageNameList;

    }


    public String getHeading() {
        return heading;
    }

    public String[] getTags() {
        return tags;
    }

    public Date getCurrentTimeZone(){
        Date date = Calendar.getInstance().getTime();
        return  date;
    }
}
