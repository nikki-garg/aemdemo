package com.mysite.core.assetapi;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component(service=DocumentUploadAssetHttp.class, immediate = true)
public class DocumentUploadAssetHttpImpl implements DocumentUploadAssetHttp{

    protected Logger LOGGER = LoggerFactory.getLogger(DocumentUploadAssetHttpImpl.class);

    @Override
    public String createFolder(String url) {


        String response = StringUtils.EMPTY;
        url+="api/assets/MyFolder";

        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin","admin");
        provider.setCredentials(AuthScope.ANY,credentials);

        HttpPost postUrl = new HttpPost(url.replace(" ","%20"));
        postUrl.setHeader("Content-Type","application/json");

        String json = "sample json "; // TODO

        try {
            StringEntity  entity = new StringEntity(json);
            postUrl.setEntity(entity);

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            CloseableHttpResponse pingResponse = client.execute(postUrl);
            response = EntityUtils.toString(pingResponse.getEntity());
        } catch (IOException e) {
            LOGGER.error("IOException",e);
        }

        return response;
    }

    @Override
    public String uploadAsset(String url) {
        String response = StringUtils.EMPTY;
        try{
            url+="api/assets/MyFolder/myAsset.pdf";
            String folderPath = "";
            String fileName = "";

            CloseableHttpResponse pingResponse = uploadAssetWithMetadata(url,folderPath,fileName);

            response = EntityUtils.toString(pingResponse.getEntity());
        } catch (IOException e) {
            LOGGER.error("IOException",e);
        }
       return response;
    }

    @Override
    public CloseableHttpResponse uploadAssetWithMetadata(String url, String folderPath, String fileName) {

        CloseableHttpResponse pingResponse = null;

        File pdfFile = new File(folderPath+"\\"+fileName+".pdf");
        
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin","admin");
        provider.setCredentials(AuthScope.ANY,credentials);

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("file",pdfFile);


        HttpEntity multipartEntity = entityBuilder.build();

        HttpPost postUrl = new HttpPost(url.replace(" ","%20"));
        postUrl.setHeader("Content-Type","application/json");
        postUrl.setEntity(multipartEntity);

        try {

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
             pingResponse = client.execute(postUrl);

             //check if success call add metadata
            pingResponse = uploadAssetWithMetadata(url,folderPath,fileName);

        }catch(IOException e){
            
        }
            
        return pingResponse;
    }

    @Override
    public CloseableHttpResponse uploadMetadata(String url, String folderPath, String fileName) {

        String metadataFilePath = folderPath+"\\"+fileName+".json";

        CloseableHttpResponse pingResponse = null;
        try {
            String metadatafileContent = new String(Files.readAllBytes(Paths.get(metadataFilePath)));

            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin","admin");
            provider.setCredentials(AuthScope.ANY,credentials);

            String json = "" ; //TODO
            StringEntity  entity = new StringEntity(json);

            HttpPut putUrl = new HttpPut(url.replace(" ","%20"));
            putUrl.setHeader("Content-Type","application/json");
            putUrl.setEntity(entity);

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            pingResponse = client.execute(putUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pingResponse;
    }
}
