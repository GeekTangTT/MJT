package org.xutils.http;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.task.Priority;
import org.xutils.common.util.LogUtil;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.http.app.RedirectHandler;
import org.xutils.http.body.BodyEntityWrapper;
import org.xutils.http.body.BodyParamsBody;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.InputStreamBody;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;

public class RequestParams
{
    private boolean asJsonContent = false;
    private boolean autoRename = false;
    private boolean autoResume = true;
    private String bodyContent;
    private LinkedHashMap<String, String> bodyParams;
    private String buildCacheKey;
    private String buildUri;
    private ParamsBuilder builder;
    private String cacheDirName;
    private final String[] cacheKeys;
    private boolean cancelFast = false;
    private String charset = "UTF-8";
    private int connectTimeout = 15000;
    private Executor executor;
    private LinkedHashMap<String, Object> fileParams;
    private HashMap<String, String> headers;
    private HttpRequest httpRequest;
    private HttpRetryHandler httpRetryHandler;
    private boolean invokedGetHttpRequest = false;
    private int maxRetryCount = 2;
    private HttpMethod method;
    private boolean multipart = false;
    private Priority priority = Priority.DEFAULT;
    private Proxy proxy;
    private LinkedHashMap<String, String> queryStringParams;
    private RedirectHandler redirectHandler;
    private RequestBody requestBody;
    private String saveFilePath;
    private final String[] signs;
    private SSLSocketFactory sslSocketFactory;
    private final String uri;

    public RequestParams()
    {
        this(null, null, null, null);
    }

    public RequestParams(String paramString)
    {
        this(paramString, null, null, null);
    }

    public RequestParams(String paramString, ParamsBuilder paramParamsBuilder, String[] paramArrayOfString1, String[] paramArrayOfString2)
    {
        Object localObject = paramParamsBuilder;
        if (paramString != null)
        {
            localObject = paramParamsBuilder;
            if (paramParamsBuilder == null) {
                localObject = new DefaultParamsBuilder();
            }
        }
        this.uri = paramString;
        this.signs = paramArrayOfString1;
        this.cacheKeys = paramArrayOfString2;
        this.builder = ((ParamsBuilder)localObject);
    }

    private void addEntityParams2Map(Class<?> paramClass)
    {
        if ((paramClass == null) || (paramClass == RequestParams.class) || (paramClass == Object.class)) {
            return;
        }
        Field[] arrayOfField = paramClass.getDeclaredFields();
        if ((arrayOfField != null) && (arrayOfField.length > 0))
        {
            int j = arrayOfField.length;
            int i = 0;
            while (i < j)
            {
                Field localField = arrayOfField[i];
                localField.setAccessible(true);
                try
                {
                    Object localObject = localField.get(this);
                    if (localObject != null) {
                        addParameter(localField.getName(), localObject);
                    }
                }
                catch (IllegalAccessException localIllegalAccessException)
                {
                    for (;;)
                    {
                        LogUtil.e(localIllegalAccessException.getMessage(), localIllegalAccessException);
                    }
                }
                i += 1;
            }
        }
        addEntityParams2Map(paramClass.getSuperclass());
    }

    private void checkBodyParams()
    {
        Object localObject1;
        Object localObject3;
        Object localObject2;
        if ((this.bodyParams != null) && ((!HttpMethod.permitsRequestBody(this.method)) || (!TextUtils.isEmpty(this.bodyContent)) || (this.requestBody != null)))
        {
            if (this.queryStringParams == null) {
                this.queryStringParams = new LinkedHashMap();
            }
            localObject1 = this.bodyParams.entrySet().iterator();
            while (((Iterator)localObject1).hasNext())
            {
                localObject3 = (Map.Entry)((Iterator)localObject1).next();
                localObject2 = (String)((Map.Entry)localObject3).getKey();
                localObject3 = (String)((Map.Entry)localObject3).getValue();
                if ((!TextUtils.isEmpty((CharSequence)localObject2)) && (localObject3 != null)) {
                    this.queryStringParams.put(localObject2, localObject3);
                }
            }
            this.bodyParams.clear();
            this.bodyParams = null;
        }
        if ((this.bodyParams != null) && ((this.multipart) || ((this.fileParams != null) && (this.fileParams.size() > 0))))
        {
            if (this.fileParams == null) {
                this.fileParams = new LinkedHashMap();
            }
            this.fileParams.putAll(this.bodyParams);
            this.bodyParams.clear();
            this.bodyParams = null;
        }
        if ((this.asJsonContent) && (this.bodyParams != null) && (!this.bodyParams.isEmpty()))
        {
            localObject1 = new JSONObject();
            localObject2 = this.bodyParams.entrySet().iterator();
            while (((Iterator)localObject2).hasNext())
            {
                Object localObject4 = (Map.Entry)((Iterator)localObject2).next();
                localObject3 = (String)((Map.Entry)localObject4).getKey();
                localObject4 = (String)((Map.Entry)localObject4).getValue();
                if ((!TextUtils.isEmpty((CharSequence)localObject3)) && (localObject4 != null)) {
                    try
                    {
                        ((JSONObject)localObject1).put((String)localObject3, localObject4);
                    }
                    catch (JSONException localJSONException)
                    {
                        throw new RuntimeException(localJSONException);
                    }
                }
            }
            setBodyContent(localJSONException.toString());
            this.bodyParams.clear();
            this.bodyParams = null;
        }
    }

    private HttpRequest getHttpRequest()
    {
        if ((this.httpRequest == null) && (!this.invokedGetHttpRequest))
        {
            this.invokedGetHttpRequest = true;
            Class localClass = getClass();
            if (localClass != RequestParams.class) {
                this.httpRequest = ((HttpRequest)localClass.getAnnotation(HttpRequest.class));
            }
        }
        return this.httpRequest;
    }

    private void initEntityParams()
    {
        addEntityParams2Map(getClass());
    }

    public void addBodyParameter(String paramString, File paramFile)
    {
        addBodyParameter(paramString, paramFile, null, null);
    }

    public void addBodyParameter(String paramString1, Object paramObject, String paramString2)
    {
        addBodyParameter(paramString1, paramObject, paramString2, null);
    }

    public void addBodyParameter(String paramString1, Object paramObject, String paramString2, String paramString3)
    {
        if (this.fileParams == null) {
            this.fileParams = new LinkedHashMap();
        }
        if ((TextUtils.isEmpty(paramString2)) && (TextUtils.isEmpty(paramString3)))
        {
            this.fileParams.put(paramString1, paramObject);
            return;
        }
        this.fileParams.put(paramString1, new BodyEntityWrapper(paramObject, paramString2, paramString3));
    }

    public void addBodyParameter(String paramString1, String paramString2)
    {
        if (this.bodyParams == null) {
            this.bodyParams = new LinkedHashMap();
        }
        this.bodyParams.put(paramString1, paramString2);
    }

    public void addHeader(String paramString1, String paramString2)
    {
        if (this.headers == null) {
            this.headers = new HashMap();
        }
        this.headers.put(paramString1, paramString2);
    }

    public void addParameter(String paramString, Object paramObject)
    {
        if (paramObject == null) {}
        do
        {
            do
            {
                return;
                if ((this.method != null) && (!HttpMethod.permitsRequestBody(this.method))) {
                    break;
                }
                if (!TextUtils.isEmpty(paramString))
                {
                    if (((paramObject instanceof File)) || ((paramObject instanceof InputStream)) || ((paramObject instanceof byte[])))
                    {
                        addBodyParameter(paramString, paramObject, null);
                        return;
                    }
                    addBodyParameter(paramString, paramObject.toString());
                    return;
                }
            } while (!TextUtils.isEmpty(paramString));
            setBodyContent(paramObject.toString());
            return;
        } while (TextUtils.isEmpty(paramString));
        addQueryStringParameter(paramString, paramObject.toString());
    }

    public void addQueryStringParameter(String paramString1, String paramString2)
    {
        if (this.queryStringParams == null) {
            this.queryStringParams = new LinkedHashMap();
        }
        this.queryStringParams.put(paramString1, paramString2);
    }

    public void clearParams()
    {
        if (this.queryStringParams != null) {
            this.queryStringParams.clear();
        }
        if (this.bodyParams != null) {
            this.bodyParams.clear();
        }
        if (this.fileParams != null) {
            this.fileParams.clear();
        }
        this.bodyContent = null;
        this.requestBody = null;
    }

    public String getBodyContent()
    {
        return this.bodyContent;
    }

    public HashMap<String, String> getBodyParams()
    {
        checkBodyParams();
        return this.bodyParams;
    }

    public String getCacheDirName()
    {
        return this.cacheDirName;
    }

    public String getCacheKey()
    {
        HttpRequest localHttpRequest;
        if ((TextUtils.isEmpty(this.buildCacheKey)) && (this.builder != null))
        {
            localHttpRequest = getHttpRequest();
            if (localHttpRequest == null) {
                break label51;
            }
        }
        label51:
        for (this.buildCacheKey = this.builder.buildCacheKey(this, localHttpRequest.cacheKeys());; this.buildCacheKey = this.builder.buildCacheKey(this, this.cacheKeys)) {
            return this.buildCacheKey;
        }
    }

    public String getCharset()
    {
        return this.charset;
    }

    public int getConnectTimeout()
    {
        return this.connectTimeout;
    }

    public Executor getExecutor()
    {
        return this.executor;
    }

    public HashMap<String, Object> getFileParams()
    {
        return this.fileParams;
    }

    public HashMap<String, String> getHeaders()
    {
        return this.headers;
    }

    public HttpRetryHandler getHttpRetryHandler()
    {
        return this.httpRetryHandler;
    }

    public int getMaxRetryCount()
    {
        return this.maxRetryCount;
    }

    public HttpMethod getMethod()
    {
        return this.method;
    }

    public Priority getPriority()
    {
        return this.priority;
    }

    public Proxy getProxy()
    {
        return this.proxy;
    }

    public HashMap<String, String> getQueryStringParams()
    {
        checkBodyParams();
        return this.queryStringParams;
    }

    public RedirectHandler getRedirectHandler()
    {
        return this.redirectHandler;
    }

    public RequestBody getRequestBody()
            throws IOException
    {
        checkBodyParams();
        Object localObject1;
        if (this.requestBody != null) {
            localObject1 = this.requestBody;
        }
        label296:
        do
        {
            Object localObject2;
            do
            {
                do
                {
                    return (RequestBody)localObject1;
                    localObject2 = null;
                    if (!TextUtils.isEmpty(this.bodyContent)) {
                        return new StringBody(this.bodyContent, this.charset);
                    }
                    if ((!this.multipart) && ((this.fileParams == null) || (this.fileParams.size() <= 0))) {
                        break label296;
                    }
                    if ((this.multipart) || (this.fileParams.size() != 1)) {
                        break;
                    }
                    localObject3 = this.fileParams.values().iterator();
                    localObject1 = localObject2;
                } while (!((Iterator)localObject3).hasNext());
                Object localObject3 = ((Iterator)localObject3).next();
                localObject1 = null;
                localObject2 = localObject3;
                if ((localObject3 instanceof BodyEntityWrapper))
                {
                    localObject1 = (BodyEntityWrapper)localObject3;
                    localObject2 = ((BodyEntityWrapper)localObject1).getObject();
                    localObject1 = ((BodyEntityWrapper)localObject1).getContentType();
                }
                if ((localObject2 instanceof File)) {
                    return new FileBody((File)localObject2, (String)localObject1);
                }
                if ((localObject2 instanceof InputStream)) {
                    return new InputStreamBody((InputStream)localObject2, (String)localObject1);
                }
                if ((localObject2 instanceof byte[])) {
                    return new InputStreamBody(new ByteArrayInputStream((byte[])localObject2), (String)localObject1);
                }
                if ((localObject2 instanceof String))
                {
                    localObject2 = new StringBody((String)localObject2, this.charset);
                    ((RequestBody)localObject2).setContentType((String)localObject1);
                    return (RequestBody)localObject2;
                }
                LogUtil.w("Some params will be ignored for: " + getUri());
                return null;
                this.multipart = true;
                return new MultipartBody(this.fileParams, this.charset);
                localObject1 = localObject2;
            } while (this.bodyParams == null);
            localObject1 = localObject2;
        } while (this.bodyParams.size() <= 0);
        return new BodyParamsBody(this.bodyParams, this.charset);
    }

    public String getSaveFilePath()
    {
        return this.saveFilePath;
    }

    public SSLSocketFactory getSslSocketFactory()
    {
        return this.sslSocketFactory;
    }

    public String getStringParameter(String paramString)
    {
        if (TextUtils.isEmpty(paramString)) {
            return this.bodyContent;
        }
        if ((this.queryStringParams != null) && (this.queryStringParams.containsKey(paramString))) {
            return (String)this.queryStringParams.get(paramString);
        }
        if ((this.bodyParams != null) && (this.bodyParams.containsKey(paramString))) {
            return (String)this.bodyParams.get(paramString);
        }
        return null;
    }

    public HashMap<String, String> getStringParams()
    {
        HashMap localHashMap = new HashMap();
        if (this.queryStringParams != null) {
            localHashMap.putAll(this.queryStringParams);
        }
        if (this.bodyParams != null)
        {
            Iterator localIterator = this.bodyParams.entrySet().iterator();
            while (localIterator.hasNext())
            {
                Object localObject = (Map.Entry)localIterator.next();
                String str = (String)((Map.Entry)localObject).getKey();
                localObject = (String)((Map.Entry)localObject).getValue();
                if ((!TextUtils.isEmpty(str)) && (localObject != null)) {
                    localHashMap.put(str, localObject);
                }
            }
        }
        return localHashMap;
    }

    public String getUri()
    {
        if (TextUtils.isEmpty(this.buildUri)) {
            return this.uri;
        }
        return this.buildUri;
    }

    void init()
            throws Throwable
    {
        if ((TextUtils.isEmpty(this.uri)) && (getHttpRequest() == null)) {
            throw new IllegalStateException("uri is empty && @HttpRequest == null");
        }
        initEntityParams();
        this.buildUri = this.uri;
        HttpRequest localHttpRequest = getHttpRequest();
        if (localHttpRequest != null)
        {
            this.builder = ((ParamsBuilder)localHttpRequest.builder().newInstance());
            this.buildUri = this.builder.buildUri(localHttpRequest);
            this.builder.buildParams(this);
            this.builder.buildSign(this, localHttpRequest.signs());
            this.sslSocketFactory = this.builder.getSSLSocketFactory();
        }
        while (this.builder == null) {
            return;
        }
        this.builder.buildParams(this);
        this.builder.buildSign(this, this.signs);
        this.sslSocketFactory = this.builder.getSSLSocketFactory();
    }

    public boolean isAsJsonContent()
    {
        return this.asJsonContent;
    }

    public boolean isAutoRename()
    {
        return this.autoRename;
    }

    public boolean isAutoResume()
    {
        return this.autoResume;
    }

    public boolean isCancelFast()
    {
        return this.cancelFast;
    }

    public boolean isMultipart()
    {
        return this.multipart;
    }

    public void removeParameter(String paramString)
    {
        if (this.queryStringParams != null) {
            this.queryStringParams.remove(paramString);
        }
        if (this.bodyParams != null) {
            this.bodyParams.remove(paramString);
        }
        if (this.fileParams != null) {
            this.fileParams.remove(paramString);
        }
    }

    public void setAsJsonContent(boolean paramBoolean)
    {
        this.asJsonContent = paramBoolean;
    }

    public void setAutoRename(boolean paramBoolean)
    {
        this.autoRename = paramBoolean;
    }

    public void setAutoResume(boolean paramBoolean)
    {
        this.autoResume = paramBoolean;
    }

    public void setBodyContent(String paramString)
    {
        this.bodyContent = paramString;
    }

    public void setCacheDirName(String paramString)
    {
        this.cacheDirName = paramString;
    }

    public void setCancelFast(boolean paramBoolean)
    {
        this.cancelFast = paramBoolean;
    }

    public void setCharset(String paramString)
    {
        if (!TextUtils.isEmpty(paramString)) {
            this.charset = paramString;
        }
    }

    public void setConnectTimeout(int paramInt)
    {
        if (paramInt > 0) {
            this.connectTimeout = paramInt;
        }
    }

    public void setExecutor(Executor paramExecutor)
    {
        this.executor = paramExecutor;
    }

    public void setHttpRetryHandler(HttpRetryHandler paramHttpRetryHandler)
    {
        this.httpRetryHandler = paramHttpRetryHandler;
    }

    public void setMaxRetryCount(int paramInt)
    {
        this.maxRetryCount = paramInt;
    }

    public void setMethod(HttpMethod paramHttpMethod)
    {
        this.method = paramHttpMethod;
    }

    public void setMultipart(boolean paramBoolean)
    {
        this.multipart = paramBoolean;
    }

    public void setPriority(Priority paramPriority)
    {
        this.priority = paramPriority;
    }

    public void setProxy(Proxy paramProxy)
    {
        this.proxy = paramProxy;
    }

    public void setRedirectHandler(RedirectHandler paramRedirectHandler)
    {
        this.redirectHandler = paramRedirectHandler;
    }

    public void setRequestBody(RequestBody paramRequestBody)
    {
        this.requestBody = paramRequestBody;
    }

    public void setSaveFilePath(String paramString)
    {
        this.saveFilePath = paramString;
    }

    public void setSslSocketFactory(SSLSocketFactory paramSSLSocketFactory)
    {
        this.sslSocketFactory = paramSSLSocketFactory;
    }

    public String toString()
    {
        return getUri();
    }
}
