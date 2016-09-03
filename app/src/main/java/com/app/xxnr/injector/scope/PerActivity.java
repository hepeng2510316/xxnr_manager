package com.app.xxnr.injector.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by CAI on 16/08/05. 标记单例模式
 */
@Scope @Retention(RUNTIME) public @interface PerActivity {
}
