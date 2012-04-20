package com.capsulecrm.rest;

import com.google.common.collect.ImmutableMap;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleTest {
    public FakeApplication fakeApplication() {
        Map<String, String> capsuleConfig = ImmutableMap.of(
                "capsulecrm.url", "https://YOURS.capsulecrm.com",
                "capsulecrm.token", "YOUR-TOKEN");

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), capsuleConfig, new ArrayList<String>());
    }
}
