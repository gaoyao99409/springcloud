package com.springcloud.test.ssq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @ClassName Test
 * @Description Test
 * @Author gaoyao
 * @Date 2021/4/27 2:04 PM
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/yaogao/Downloads/ssq.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bufr = new BufferedReader(fr);
        String line = null;
        //BufferedReader提供了按行读取文本文件的方法readLine()
        //readLine()返回行有效数据，不包含换行符，未读取到数据返回null


        while((line = bufr.readLine())!=null) {
            String[] str = line.split("\t");
            for (int i=0; i< 8; i++) {
                System.out.print(str[i]+",");
            }
            System.out.println(str[str.length-1]);
        }
    }

}
