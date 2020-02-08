package com.forestj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work implements Serializable {
    //作业id
    @TableId
    private String workId;
    //考核id
    private String ExamId;
    //学生姓名
    private String name;
    //学号
    private String stuId;
    //提交时间
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    private Date submitDate;
    //审核人
    private String checker;
    //作业状态 0审核中，1通过，2未通过
    private Integer state;
    //作业对应的文件名
    @JsonIgnore
    private String filename;
}
