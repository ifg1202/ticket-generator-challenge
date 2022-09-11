package com.lindar;

import com.lindar.strip.StripFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    App app = new App();

    @Test
    void tenThousandStripsCreationTimeLessThanOneSecond() {
        //given
        int strips = 10000;
        long oneSecond = 1000;
        //when
        Instant start = Instant.now();
        app.start(strips);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        //then
        assertThat(timeElapsed).isLessThanOrEqualTo(oneSecond);
    }

}