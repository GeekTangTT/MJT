package org.xutils.view.annotation;

import android.view.View.OnClickListener;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface Event
{
    String method() default "";

    int[] parentId() default {0};

    String setter() default "";

    Class<?> type() default View.OnClickListener.class;

    int[] value();
}
