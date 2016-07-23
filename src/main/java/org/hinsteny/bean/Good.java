package org.hinsteny.bean;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
public class Good {

    private String uuid;
    private String goodName;
    private int category;
    private String description;

    private LocalDateTime createTime;

    public Good( ) {
        this.uuid = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
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
