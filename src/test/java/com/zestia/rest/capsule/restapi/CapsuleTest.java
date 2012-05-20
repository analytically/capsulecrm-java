package com.zestia.rest.capsule.restapi;

import com.google.common.collect.ImmutableMap;
import play.mvc.Http;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleTest {
    public FakeApplication fakeApplication() {
        Map<String, String> capsuleConfig = ImmutableMap.of(
                "capsulecrm.url", "https://YOURS.capsulecrm.com",
                "capsulecrm.token", "YOURTOKEN");

        // needed since https://github.com/playframework/Play20/commit/e8ff62bb77f4856a02ab39519d519322a4bcd2db
        Http.Context context = new Http.Context(null, new HashMap<String, String>(), new HashMap<String, String>());
        Http.Context.current.set(context);

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), capsuleConfig, new ArrayList<String>());
    }
}
