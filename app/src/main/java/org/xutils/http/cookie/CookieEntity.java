package org.xutils.http.cookie;

import android.text.TextUtils;
import java.net.HttpCookie;
import java.net.URI;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="cookie", onCreated="CREATE UNIQUE INDEX index_cookie_unique ON cookie(\"name\",\"domain\",\"path\")")
final class CookieEntity
{
    private static final long MAX_EXPIRY = System.currentTimeMillis() + 3110400000000L;
    @Column(name="comment")
    private String comment;
    @Column(name="commentURL")
    private String commentURL;
    @Column(name="discard")
    private boolean discard;
    @Column(name="domain")
    private String domain;
    @Column(name="expiry")
    private long expiry = MAX_EXPIRY;
    @Column(isId=true, name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="path")
    private String path;
    @Column(name="portList")
    private String portList;
    @Column(name="secure")
    private boolean secure;
    @Column(name="uri")
    private String uri;
    @Column(name="value")
    private String value;
    @Column(name="version")
    private int version = 1;

    public CookieEntity() {}

    public CookieEntity(URI paramURI, HttpCookie paramHttpCookie)
    {
        if (paramURI == null) {}
        for (paramURI = null;; paramURI = paramURI.toString())
        {
            this.uri = paramURI;
            this.name = paramHttpCookie.getName();
            this.value = paramHttpCookie.getValue();
            this.comment = paramHttpCookie.getComment();
            this.commentURL = paramHttpCookie.getCommentURL();
            this.discard = paramHttpCookie.getDiscard();
            this.domain = paramHttpCookie.getDomain();
            long l = paramHttpCookie.getMaxAge();
            if (l != -1L)
            {
                this.expiry = (1000L * l + System.currentTimeMillis());
                if (this.expiry < 0L) {
                    this.expiry = MAX_EXPIRY;
                }
            }
            this.path = paramHttpCookie.getPath();
            if ((!TextUtils.isEmpty(this.path)) && (this.path.length() > 1) && (this.path.endsWith("/"))) {
                this.path = this.path.substring(0, this.path.length() - 1);
            }
            this.portList = paramHttpCookie.getPortlist();
            this.secure = paramHttpCookie.getSecure();
            this.version = paramHttpCookie.getVersion();
            return;
        }
    }

    public long getId()
    {
        return this.id;
    }

    public String getUri()
    {
        return this.uri;
    }

    public void setId(long paramLong)
    {
        this.id = paramLong;
    }

    public void setUri(String paramString)
    {
        this.uri = paramString;
    }

    public HttpCookie toHttpCookie()
    {
        HttpCookie localHttpCookie = new HttpCookie(this.name, this.value);
        localHttpCookie.setComment(this.comment);
        localHttpCookie.setCommentURL(this.commentURL);
        localHttpCookie.setDiscard(this.discard);
        localHttpCookie.setDomain(this.domain);
        localHttpCookie.setMaxAge((this.expiry - System.currentTimeMillis()) / 1000L);
        localHttpCookie.setPath(this.path);
        localHttpCookie.setPortlist(this.portList);
        localHttpCookie.setSecure(this.secure);
        localHttpCookie.setVersion(this.version);
        return localHttpCookie;
    }
}
