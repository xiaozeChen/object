package com.cxz.annotation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/10/31.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BNotInherited {
    String value();
}
