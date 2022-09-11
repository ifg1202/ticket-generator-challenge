package com.lindar;

import com.lindar.strip.StripFactory;

import java.util.stream.IntStream;

public class App {
    public void start(int strips) {
        IntStream.range(0, strips).forEach(x -> StripFactory.getStrip());
    }
}
