package com.mysite.core.assetapi;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(immediate = true , service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/uploadAsset"
        })
public class DocumentHttpServlet extends SlingSafeMethodsServlet {

    @Reference
    DocumentUploadAssetHttp service;

    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) throws IOException {
        String url = "http://localhost:4503";
        String folderPath = "";
        String fileName = "";

        CloseableHttpResponse clientResponse = service.uploadAssetWithMetadata(url,folderPath,fileName);
        response.getWriter().write("ClientResponse "+clientResponse);
    }
}
