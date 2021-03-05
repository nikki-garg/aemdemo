package com.mysite.core.models;

import com.mysite.core.beans.TileBean;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class)
public class TilesModel {

    @SlingObject
    private Resource currentResource;

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
