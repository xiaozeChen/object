package com.cxz.annotation;

import com.cxz.annotation.annotations.AInherited;
import com.cxz.annotation.annotations.BNotInherited;

/**
 * Created by Administrator on 2017/10/31.
 */
@AInherited("Inherited")
@BNotInherited("NotInherited")
public class Parent {
    @AInherited("Inherited")
    @BNotInherited("NotInherited")
    public void testOverride() {

    }

    @AInherited("Inherited")
    @BNotInherited("NotInherited")
    public void testNotOverride() {

    }
}
