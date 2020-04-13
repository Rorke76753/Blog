package edu.rorke.blog.background;

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
        int i = 3;
        int result = i++*i++*i++;
        //3*4*5
        System.out.println(result);
        int result2 = result++ + ++result;
        System.out.println(result2);
        //60+62
    }
}
