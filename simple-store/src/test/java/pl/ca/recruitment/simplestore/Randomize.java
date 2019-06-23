package pl.ca.recruitment.simplestore;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class Randomize {
    private static final EasyRandom innerRandom = new EasyRandom();

    private static final EasyRandom random = new EasyRandom(
            new EasyRandomParameters()
                    .seed(500L)
                    .randomize(Integer.class, () -> Math.abs(innerRandom.nextInt()))
                    .randomize(Long.class, () -> Math.abs(innerRandom.nextLong()))
    );

    public static <T> T random(Class<T> clazz) {
        return random.nextObject(clazz);
    }
}
