package edu.rorke.blog.background;

import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.result.internal.OutputsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.security.provider.DSAPrivateKey;

import java.util.Stack;

/**
 * @author Rorke
 * @date 2020/3/27 0:22
 */
public class SimpleTest {
    @Test
    void divideTest(){
        System.out.println(new BCryptPasswordEncoder().encode("Fuck1998*"));
    }
}
