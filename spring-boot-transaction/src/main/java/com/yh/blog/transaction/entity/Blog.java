package com.yh.blog.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName Blog.java
 * @Description 博客实体类
 * @Param
 * @createTime 2020年01月02日 16:23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String remark;

    public Blog(String title, String content, String remark) {
        this.title = title;
        this.content = content;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
