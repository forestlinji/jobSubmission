package com.forestj.controller;

import com.forestj.pojo.*;
import com.forestj.service.ExamService;
import com.forestj.service.WorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/exam")
@Api(tags = "考核模块")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private WorkService workService;

    @PostMapping("/addExam")
    @ApiOperation(value = "发布考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deadline",paramType="form")
    })
    public ResponseJson addExam(String title,
                                String content,
                                String name,
                                @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss") Date deadline){
        Exam exam=new Exam(UUID.randomUUID().toString(),title,content,name,deadline);
        examService.addExam(exam);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "查看所有考核")
    public ResponseJson<PageResult<Exam>> getAll(int page){
        if(page<=0){
            return new ResponseJson<>(ResultCode.WRONGEXAMPAGE);
        }
        PageResult<Exam> result = examService.getAll(page);
        return new ResponseJson<>(ResultCode.SUCCESS,result);
    }

    @GetMapping("/getExam")
    @ApiOperation(value = "查看单个考核")
    public ResponseJson<Exam> getExam(String examId){
        Exam exam = examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        return new ResponseJson<>(ResultCode.SUCCESS,exam);
    }

    @PostMapping("/updateExam")
    @ApiOperation(value = "修改考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deadline",paramType="form")
    })
    public ResponseJson updateExam(String examId,
                                   String title,
                                   String content,
                                   String name,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")  Date deadline){
        Exam exam=examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        exam=new Exam(examId,title,content,name,deadline);
        examService.updateExam(exam);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除考核")
    public ResponseJson delete(String examId){
        Exam exam=examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        //删除考核的同时删除下属作业
        List<Work> works = workService.getWorks(examId);
        for(Work work:works){
            workService.deleteWorkById(work);
        }
        examService.deleteById(examId);
        return new ResponseJson(ResultCode.SUCCESS);
    }

}
