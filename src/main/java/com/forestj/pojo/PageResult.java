package com.forestj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    //查询数据列表
    private List<T> records;

    //总页数
    private long total;

    //每页显示条数，默认 10
    //private long size;

    //当前页
    private long current;

    //查询到的结果数
    private long count;
}
