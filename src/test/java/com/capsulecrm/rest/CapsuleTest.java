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
                "capsulecrm.url", "https://pearsons.capsulecrm.com",
                "capsulecrm.token", "18a17b50818411e6ba4a48e93d144444");

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), capsuleConfig, new ArrayList<String>());
    }
}
