package com.test.xstream;

import com.test.xstream.converter.LocalDateConverter;
import com.test.xstream.entity.Birthday;
import com.test.xstream.entity.Student;
import com.thoughtworks.xstream.XStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hinsteny
 * @date 2016/10/14
 * @copyright: 2016 All rights reserved.
 */
public class XStreamTest {

    @Before
    public void init() {
    }

    @After
    public void destory() {
        System.gc();
    }

    public final void println(String string) {
        System.out.println(string);
    }

    public final void failRed(String string) {
        System.err.println(string);
    }

    /**
     * <b>function:</b>Java对象转换成XML字符串
     * @author Hinsteny
     * @date 2016/10/14
     */
    @Test
    public void writeBean2XML() {
        try {
            println(DateTimeFormatter.ISO_LOCAL_DATE.toString());
            println("------------Bean  -> Create------------");
            Student bean = new Student();
            bean = new Student();
            bean.setAddress("china");
            bean.setEmail("yxr@gmail.com");
            bean.setId(1);
            bean.setName("yxr");
            Birthday day = new Birthday();
            day.setDate(LocalDate.now());
            bean.setBirthday(day);

            println("------------Bean  -> println XML------------");
            XStream xstream = new XStream();
            xstream.registerConverter(new LocalDateConverter());
            String beanStr = xstream.toXML(bean);
            println(beanStr);
            //包重命名
            xstream.aliasPackage("xstream", "com.test.xstream.entity");
            println(xstream.toXML(bean));
            println("------------Bean  -> change, println XML------------");
            //类重命名
            xstream.alias("account", Student.class);
            xstream.aliasField("生日", Student.class, "birthday");
            println(xstream.toXML(bean));
            //属性重命名
            xstream.aliasField("邮件", Student.class, "email");

            //parse还原xml字符串为bean对象
            Student old = (Student)xstream.fromXML(beanStr);
            println(old.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>function:</b>将Java的List集合转换成XML对象
     * @author Hinsteny
     * @date 2016/10/14
     */
    @Test
    public void writeList2XML() {
        try {
            XStream xstream = new XStream();
            xstream.registerConverter(new LocalDateConverter());
            println("----------List-->XML----------");
            Student.StuList listBean = new Student.StuList();
            listBean.setName("this is a List Collection");
            List<Student> list = new ArrayList();

            Student bean = new Student();
            list.add(bean);
            bean.setAddress("china");
            bean.setEmail("yxr@gmail.com");
            bean.setId(2);
            bean.setName("yxr");
            Birthday day = new Birthday();
            day.setTime("2016-10-24");
            bean.setBirthday(day);
            list.add(bean);//引用bean
            //list.add(listBean);//引用listBean，父元素

            list.add(bean);
            listBean.setStudents(list);
            //将ListBean中的集合设置空元素，即不显示集合元素标签
            //xstream.addImplicitCollection(ListBean.class, "list");

            //修改元素名称
            xstream.alias("beans", Student.StuList.class);
            xstream.alias("student", Student.class);
            //设置reference模型
            xstream.setMode(XStream.NO_REFERENCES);//不引用
//            xstream.setMode(XStream.ID_REFERENCES);//id引用
//            xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);//绝对路径引用

            //将name设置为父类（Student）的元素的属性
            xstream.useAttributeFor(Student.class, "name");
            xstream.useAttributeFor(Birthday.class, "time");
            //修改属性的name
            xstream.aliasAttribute("姓名", "name");
            xstream.aliasField("邮件", Student.class, "email");
            xstream.aliasField("生日", Birthday.class, "birthday");
            println(xstream.toXML(listBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
