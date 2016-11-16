package org.hinsteny.bean.socket;

import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
public class Greeting {

    private String content;
    private String time;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
        this.time = LocalDateTime.now().toString();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
