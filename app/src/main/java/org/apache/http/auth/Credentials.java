package org.apache.http.auth;

import java.security.Principal;

@Deprecated
public abstract interface Credentials
{
    public abstract String getPassword();

    public abstract Principal getUserPrincipal();
}
