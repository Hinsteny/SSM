package com.test.others;

import com.hisoka.other.groovy.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hinsteny on 2015/12/25.
 */
public class TestGroovyObject {

    @Test
    public void useGroovyObject(){
        Person person = new Person();
        person.setName("Hinsteny");
        person.doTest();
        System.out.println(person);
        Object o = "Object";
        int result = person.method(o);
        assertEquals(2, result);
    }

    @Test
    public void useGroovyLikeLambads(){
        Person person = new Person();
        person.setName("Hinsteny");
        person.likeLambdas();
        List<String> x = new ArrayList<String>();
        x.add("A");
        x.add("B");
        x.add("C");
        Consumer cu =((i) -> System.out.println("accept: "+i));

        cu.andThen((i) -> System.out.println("then: "+i));
        x.forEach(cu);
//        x.forEach((i) -> System.out.println(i));
        for (String y : x){
            acceptConsumer(y, cu);
        }
    }

    private void acceptConsumer(Object object, Consumer consumer){
        consumer.accept(object);
        consumer.andThen(consumer);
    }
}
