package com.cycas.util.dto;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpConfigDTO {

    private HttpClient client;
    private Header[] headers;
    private boolean isReturnRespHeaders;
    private String methodName;
    private HttpContext context;
    private Map<String, String> paramMap = new HashMap();
    private String url;
    private String encoding = Charset.defaultCharset().displayName();
    private String inenc;
    private String outenc;
    private RequestConfig requestConfig;
    private String contentType;
    private List<File> fileList = new ArrayList();
    private String params;
    private String fileName;

    private HttpConfigDTO() {
    }

    public static HttpConfigDTO custom() {
        return new HttpConfigDTO();
    }

    public HttpConfigDTO client(HttpClient client) {
        this.client = client;
        return this;
    }

    public HttpConfigDTO url(String url) {
        this.url = url;
        return this;
    }

    public HttpConfigDTO headers(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public HttpConfigDTO headers(Header[] headers, boolean isReturnRespHeaders) {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }

    public HttpConfigDTO methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public HttpConfigDTO context(HttpContext context) {
        this.context = context;
        return this;
    }

    public HttpConfigDTO paramMap(Map<String, String> map) {
        this.paramMap = map;
        return this;
    }

    public HttpConfigDTO encoding(String encoding) {
        this.inenc(encoding);
        this.outenc(encoding);
        this.encoding = encoding;
        return this;
    }

    public HttpConfigDTO inenc(String inenc) {
        this.inenc = inenc;
        return this;
    }

    public HttpConfigDTO outenc(String outenc) {
        this.outenc = outenc;
        return this;
    }

    public HttpConfigDTO timeout(int timeout) {
        return this.timeout(timeout, true);
    }

    public HttpConfigDTO timeout(int timeout, boolean redirectEnable) {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).setRedirectsEnabled(redirectEnable).build();
        return this.timeout(config);
    }

    public HttpConfigDTO timeout(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public HttpConfigDTO contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpConfigDTO fileList(List<File> fileList) {
        this.fileList = fileList;
        return this;
    }

    public HttpConfigDTO params(String params) {
        this.params = params;
        return this;
    }

    public HttpConfigDTO fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public HttpClient client() {
        return this.client;
    }

    public Header[] headers() {
        return this.headers;
    }

    public boolean isReturnRespHeaders() {
        return this.isReturnRespHeaders;
    }

    public String url() {
        return this.url;
    }

    public String methodName() {
        return this.methodName;
    }

    public HttpContext context() {
        return this.context;
    }

    public Map<String, String> paramMap() {
        return this.paramMap;
    }

    public String encoding() {
        return this.encoding;
    }

    public String inenc() {
        return this.inenc == null ? this.encoding : this.inenc;
    }

    public String outenc() {
        return this.outenc == null ? this.encoding : this.outenc;
    }

    public RequestConfig requestConfig() {
        return this.requestConfig;
    }

    public String contentType() {
        return this.contentType;
    }

    public List<File> fileList() {
        return this.fileList;
    }

    public String params() {
        return this.params;
    }

    public String fileName() {
        return this.fileName;
    }

}
