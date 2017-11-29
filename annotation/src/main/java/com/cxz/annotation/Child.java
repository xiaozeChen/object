package com.cxz.annotation;

/**
 * Created by Administrator on 2017/10/31.
 */

public class Child extends Parent {
    /**
     * 重写的testOverride不继承任何注解
     * 因为Inherited不作用在方法上
     */
    @Override
    public void testOverride() {
        super.testOverride();
    }

    /**
     *testNotOverride没有被重写
     * 所以注解AInherited和BNotInherited依然生效。
     */
//    @Override
//    public void testNotOverride() {
//        super.testNotOverride();
//    }
}
