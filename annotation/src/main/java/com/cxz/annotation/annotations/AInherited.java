package com.cxz.annotation.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/10/31.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AInherited {
    String value();
}

