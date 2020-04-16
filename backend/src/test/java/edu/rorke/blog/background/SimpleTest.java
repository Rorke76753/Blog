package edu.rorke.blog.background;

import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.result.internal.OutputsImpl;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @author Rorke
 * @date 2020/3/27 0:22
 */
public class SimpleTest {
    @Test
    void divideTest(){
        Double test = 1.0D;
        System.out.println(test*10);
        Double test1 = 1.0001D;
        System.out.println(test1*10);
    }
}
