package com.kitchent.api.controller;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOidcUserSecurityContextFactory.class)
public @interface WithMockOidcUser {
    String email() default "testuser@example.com";
    String sub() default "test-sub-123";
    String name() default "Test User";
}
