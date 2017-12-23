package com.zlcm.zlgg.di.qualifiler;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ZLUrl {
}
