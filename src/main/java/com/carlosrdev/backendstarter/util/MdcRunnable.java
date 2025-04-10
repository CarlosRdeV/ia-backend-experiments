package com.carlosrdev.backendstarter.util;

import org.slf4j.MDC;

import java.util.Map;

public class MdcRunnable implements Runnable {

    private final Runnable delegate;
    private final Map<String, String> contextMap;

    public MdcRunnable(Runnable delegate) {
        this.delegate = delegate;
        this.contextMap = MDC.getCopyOfContextMap();
    }

    @Override
    public void run() {
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
        try {
            delegate.run();
        } finally {
            MDC.clear();
        }
    }

    public static Runnable wrap(Runnable runnable) {
        return new MdcRunnable(runnable);
    }
}
