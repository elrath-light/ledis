package me.ledis;

import me.ledis.data.LedisData;
import me.ledis.factory.CommandExecutorFactory;
import org.junit.jupiter.api.AfterEach;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class LedisTest {
    private CommandExecutorFactory factory = new CommandExecutorFactory();
    @AfterEach
    public void cleanUp() {
        LedisData.clear();
    }

    public CommandExecutorFactory getFactory() {
        return factory;
    }

    public void initSampleList() {
        List<String> elements = new LinkedList<>();
        elements.add("one");
        elements.add("two");
        elements.add("three");
        LedisData.set("elements", elements);
    }

    public void initSampleSet() {
        Set<String> mySet = new HashSet<>();
        mySet.add("one");
        mySet.add("two");
        mySet.add("three");
        LedisData.set("mySet", mySet);
    }
}
