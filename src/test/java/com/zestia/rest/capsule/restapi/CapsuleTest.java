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
                "capsulecrm.url", "https://pearsons.capsulecrm.com",
                "capsulecrm.token", "e1d2dacc6dfa2213635c458beb86674c");

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), capsuleConfig, new ArrayList<String>());
    }
}
