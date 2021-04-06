package com.mysite.core.assetapi;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface DocumentUploadAssetHttp {

    String createFolder(String url);

    String uploadAsset(String url);

    CloseableHttpResponse uploadAssetWithMetadata(String url, String folderPath, String fileName);

    CloseableHttpResponse uploadMetadata(String url, String folderPath, String fileName);
}
