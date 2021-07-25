package jbsdemo.bean;

import lombok.Data;

/**
 * @ClassName CalLinkBean
 * @Description CalLinkBean
 * @Author gaoyao
 * @Date 2021/7/20 3:30 PM
 * @Version 1.0
 */
@Data
public class CalLinkBean<A, B> {

    public CalLinkBean(A a, B b) {
        this.a = a;
        this.b = b;
    }
    public CalLinkBean(){}

    private A a;
    private B b;

}
