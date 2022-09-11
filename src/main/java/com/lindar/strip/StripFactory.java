package com.lindar.strip;

import com.lindar.utils.RandomEngine;
import com.lindar.utils.RandomEngineImpl;

public class StripFactory {

    private static final RandomEngine randomEngine = new RandomEngineImpl();

    public static Strip getStrip() {
        StripImpl stripImpl = new StripImpl(randomEngine);
        stripImpl.create();
        return stripImpl;
    }
}
