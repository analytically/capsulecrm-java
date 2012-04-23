package com.zestia.rest.capsule.restapi;

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
                "capsulecrm.url", "https://YOURSUBDOMAIN.capsulecrm.com",
                "capsulecrm.token", "YOURTOKEN");

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), capsuleConfig, new ArrayList<String>());
    }
}
