package com.mysite.core.servlets;

import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;

@Component(immediate = true , service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/demo"
        })

public class DemoServlet extends SlingSafeMethodsServlet {

    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response){

        try {
            String a = request.getParameter("a");

            ResourceResolver resolver = request.getResourceResolver();
            String title = StringUtils.EMPTY;
            String template = "";

            Resource res = resolver.getResource("/content/mysite/us/en/main-site-page");
            if(null!=res){
                Page page = res.adaptTo(Page.class);
                title = page.getTitle();


                ValueMap properties = res.getChild("jcr:content").getValueMap();
                template = properties.get("cq:template",String.class);

                ModifiableValueMap valuemap = page.getContentResource().adaptTo(ModifiableValueMap.class);
                valuemap.put("name","nikita");
                resolver.commit();
            }

            //Reding Node

            PrintWriter writer =  response.getWriter();
            writer.write("Demo Servlet "+ title + template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
