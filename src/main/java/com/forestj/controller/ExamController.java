package com.forestj.controller;

import com.forestj.pojo.*;
import com.forestj.service.ExamService;
import com.forestj.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private WorkService workService;

    @PostMapping("/addExam")
    public ResponseJson addExam(String title,
                                String content,
                                String name,
                                @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss") Date deadline){
        Exam exam=new Exam(UUID.randomUUID().toString(),title,content,name,deadline);
        examService.addExam(exam);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/getAll")
    public ResponseJson<PageResult<Exam>> getAll(int page){
        if(page<=0){
            return new ResponseJson<>(ResultCode.WRONGEXAMPAGE);
        }
        PageResult<Exam> result = examService.getAll(page);
        return new ResponseJson<>(ResultCode.SUCCESS,result);
    }

    @GetMapping("/getExam")
    public ResponseJson<Exam> getExam(String examId){
        Exam exam = examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        return new ResponseJson<>(ResultCode.SUCCESS,exam);
    }

    @PostMapping("/updateExam")
    public ResponseJson updateExam(String examId,
                                   String title,
                                   String content,
                                   String name,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss") Date deadline){
        Exam exam=examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        exam=new Exam(examId,title,content,name,deadline);
        examService.updateExam(exam);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/delete")
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
