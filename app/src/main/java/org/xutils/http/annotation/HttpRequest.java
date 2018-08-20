package org.xutils.http.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.xutils.http.app.DefaultParamsBuilder;
import org.xutils.http.app.ParamsBuilder;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface HttpRequest
{
    Class<? extends ParamsBuilder> builder() default DefaultParamsBuilder.class;

    String[] cacheKeys() default {""};

    String host() default "";

    String path();

    String[] signs() default {""};
}

