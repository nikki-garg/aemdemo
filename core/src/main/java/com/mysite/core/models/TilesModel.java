package com.mysite.core.models;

import com.day.cq.wcm.api.Page;
import com.mysite.core.beans.TileBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class)
public class TilesModel {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resolver;



    @Inject
    private List<Resource> tiles;

    public String getTitle(){
        String title = StringUtils.EMPTY;
        Resource pageRes = resolver.getResource("/content/mysite/us/en/demo-page/demo-3");
        Page page = pageRes.adaptTo(Page.class);
        if(null!=page){
            title = page.getTitle();
        }
        return title;
    }

    public  String getNodeTitle()  {
        String title = "";//StringUtils.EMPTY;
        Resource pageRes = resolver.getResource("/content/mysite/us/en/demo-page/demo-3");
        Node node = pageRes.adaptTo(Node.class);
        try {
            Node jcrNode = node.getNode(Node.JCR_CONTENT);
            Property property = jcrNode.getProperty("jcr:title");
            title = property.getValue().getString();
        }catch (RepositoryException e){
            log.error("Repository Exception ",e);
        }
        return title;
    }

    public List<TileBean> getList(){

        List<TileBean> beanList = new ArrayList<TileBean>();
        for(Resource res : tiles){
            ValueMap properties = res.getValueMap();
            TileBean bean = new TileBean();
            bean.setDescription(properties.get("description",String.class));
            bean.setIconClass(properties.get("iconclass",String.class));
            bean.setTitle(properties.get("title",String.class));
            beanList.add(bean);
        }
     return  beanList;
    }

    public List<TileBean> getTileList(){
        List<TileBean> beanList = new ArrayList<TileBean>();
        Resource tilesResource = currentResource.getChild("tiles");

        Iterator<Resource> iterator = tilesResource.listChildren();

        while(iterator.hasNext()){
            Resource itemRes = iterator.next();
            ValueMap properties = itemRes.getValueMap();
            TileBean bean = new TileBean();
            bean.setDescription(properties.get("description",String.class));
            bean.setIconClass(properties.get("iconclass",String.class));
            bean.setTitle(properties.get("title",String.class));
            beanList.add(bean);
        }
       return beanList;

    }
}
