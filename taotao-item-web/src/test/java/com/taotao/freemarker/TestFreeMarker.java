package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;



public class TestFreeMarker {
    @Test
    public void testFreemarker() throws Exception {
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("D:/itcast-workspace/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
        //4.设置模板字符集，utf-8
        configuration.setDefaultEncoding("utf-8");
        //5.使用Configuration对象加载模板文件，需要指定一个文件名
        Template template = configuration.getTemplate("hello.ftl");
        //6.创建一个数据集，推荐使用map
        Map data = new HashMap<>();
        data.put("hello", "hello freemarker");
        //7.创建一个writer对象，指定输出文件路径及文件名
        Writer out = new FileWriter(new File("D:/out/hello.txt"));
        //8.使用模板对象的process方法输出文件
        template.process(data, out);
        //9.关闭流
        out.close();
    }
}
