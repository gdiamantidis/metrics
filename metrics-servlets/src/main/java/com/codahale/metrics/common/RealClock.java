package com.codahale.metrics.common;

import java.util.Date;

public class RealClock implements SystemClock {

    public static final String CLOCK = RealClock.class.getCanonicalName() + ".clock";

    @Override
    public Date now() {
        return new Date();
    }
}
