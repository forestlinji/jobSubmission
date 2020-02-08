package com.forestj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam implements Serializable {
    //考核id
    @TableId
    private String examId;
    //考核标题
    private String title;
    //考核内容
    private String content;
    //发布人名字
    private String name;
    //截止日期
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    private Date deadline;
}
