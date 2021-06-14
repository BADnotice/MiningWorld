package io.github.badnotice.miningworld.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PercentageUtils {

    public static boolean inPercent(double percent) {
        return Math.random() * 100.0 <= percent;
    }

}
