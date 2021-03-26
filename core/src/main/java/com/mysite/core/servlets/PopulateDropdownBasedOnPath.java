package com.mysite.core.servlets;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.util.*;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/apps/dropdownList"

        })
@ServiceDescription("Populate Dropdown Servlet")
public class PopulateDropdownBasedOnPath extends SlingSafeMethodsServlet {

    private Logger LOGGER = LoggerFactory.getLogger(PopulateDropdownBasedOnPath.class);

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) {
        try {
            String path = "/content/we-retail/us/en/products/equipment";
            ResourceResolver resourceResolver = request.getResourceResolver();
            Map<String, String> dropDownMap = new HashMap<>();
            Resource pathRes = resourceResolver.getResource(path);
            LOGGER.debug("Path Res {}", pathRes);
            if (pathRes != null) {
                Page page = pathRes.adaptTo(Page.class);
                if (page != null) {
                    Iterator<Page> iterator = page.listChildren();
                    List<Page> list = new ArrayList<>();
                    iterator.forEachRemaining(list::add);
                    list.forEach(pageChild -> {
                        String name = pageChild.getName();
                        String title = pageChild.getTitle();
                        dropDownMap.put(name, title);
                    });
                }

            }
            @SuppressWarnings("unchecked")
            DataSource ds =
                    new SimpleDataSource(
                            new TransformIterator(
                                    dropDownMap.keySet().iterator(),
                                    new Transformer() {
                                        @Override
                                        public Object transform(Object o) {
                                            String key = (String) o;
                                            ValueMap vm = new ValueMapDecorator(new HashMap<>());
                                            vm.put("value", key);
                                            vm.put("text", dropDownMap.get(key));
                                            return new ValueMapResource(
                                                    resourceResolver, new ResourceMetadata(),
                                                    JcrConstants.NT_UNSTRUCTURED, vm);
                                        }
                                    }

                            ));
            request.setAttribute(DataSource.class.getName(), ds);
        } catch (Exception e) {
            LOGGER.error("Error in Get Drop Down Values", e);
        }
    }


}
