package edu.rorke.blog.background.management;

import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.result.internal.OutputsImpl;
import org.junit.jupiter.api.Test;

/**
 * @author Rorke
 * @date 2020/3/27 0:22
 */
public class SimpleTest {
    @Test
    void divideTest(){
        Output output = new Output() {
            @Override
            public boolean isResultSet() {
                return false;
            }
        };
        System.out.println(ResultSetOutput.class.isInstance(output));
    }
}
