package org.hinsteny.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
public class Book {

    private String uuid;
    private String bookName;
    private int category;
    private String description;

    private LocalDateTime createTime;

    public Book( ) {
        this.uuid = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
