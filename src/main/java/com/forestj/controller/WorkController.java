package com.forestj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forestj.mapper.ExamMapper;
import com.forestj.pojo.*;
import com.forestj.service.ExamService;
import com.forestj.service.WorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/work")
@Slf4j
@Api(tags = "作业模块")
public class WorkController {
    @Autowired
    private ExamService examService;
    @Autowired
    private WorkService workService;

    @PostMapping("/upload")
    @ApiOperation(value = "作业上传")
    public ResponseJson upload(MultipartFile files,
                               String examId,
                               String name,
                               String stuId) throws IOException {
        //判断文件格式
        String suffixList = "rar,zip,tar.gz,7z";
        String uploadFileName = files.getOriginalFilename();
        String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".")
                + 1, uploadFileName.length());
        if(!suffixList.contains(suffix)){
            return new ResponseJson(ResultCode.WRONGFORMAT);
        }
        Exam exam = examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson(ResultCode.EMPTYEXAM);
        }
        //判断是否超过截止时间
        Date submitDate = new Date();
        if(exam.getDeadline().getTime()<submitDate.getTime()){
            return new ResponseJson(ResultCode.OUTOFTIME);
        }
        boolean exist = false;
        Work work = workService.selectWorkByNameAndId(name,stuId);
        String fileName = name+stuId+exam.getTitle()+"."+suffix;  //学号姓名考核名.后缀
        if(work != null){   //非第一次提交作业，进行更新
            exist = true;
            workService.deleteWorkById(work);
            work.setFilename(fileName);
            work.setSubmitDate(new Date());
            work.setChecker("");
            work.setState(0);
        }
        else{   //第一次提交作业
            work = new Work(UUID.randomUUID().toString(),examId,name,stuId,new Date(),null,0,fileName);
        }
        String path = "workFile/"+examId+"/"+work.getFilename();
        File dest=new File(path);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
            log.debug("创建目录"+path);
        }
        try {
            files.transferTo(dest);
            log.debug("创建文件"+fileName);
            workService.addWork(work);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        ResponseJson res;
        if(exist == false){
            res = new ResponseJson(ResultCode.SUCCESS);
        }
        else {
            res = new ResponseJson(ResultCode.UPDATED);
        }
        return res;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "查看某考核对应的作业")
    public ResponseJson<PageResult<Work>> getAllWork(String examId,int page){
        Exam exam = examService.getExamById(examId);
        if(exam==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        PageResult<Work> allWorks = workService.getAllWorks(examId, page);
        return new ResponseJson<>(ResultCode.SUCCESS,allWorks);
    }

    @GetMapping("/updateWork")
    @ApiOperation(value = "修改作业状态")
    public ResponseJson updateWork(String workId,
                                   @RequestParam(defaultValue = "") String checker,
                                   @RequestParam(defaultValue = "-1")int state){
        if(state!=0&&state!=1&&state!=2){
            return new ResponseJson(ResultCode.WRONGSTATE);
        }
        Work work = workService.getWorkById(workId);
        if(work==null){
            return new ResponseJson<>(ResultCode.EMPTYEXAM);
        }
        work.setChecker(checker);
        work.setState(state);
        workService.addWork(work);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/download")
    @ApiOperation(value = "删除单个作业")
    public ResponseJson download(HttpServletResponse res,String workId){
        Work work = workService.getWorkById(workId);
        if(work==null){
            return new ResponseJson(ResultCode.EMPTYWORK);
        }
        try(InputStream inputStream = new FileInputStream(new File("workFile/"+work.getExamId(),work.getFilename()));
            OutputStream outputStream = res.getOutputStream();) {
            //设置内容类型为下载类型
            res.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            res.addHeader("Content-Disposition","attachment;filename="+work.getFilename());
            //用 common-io 工具 将输入流拷贝到输出流
            IOUtils.copy(inputStream,outputStream);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除单个作业")
    public ResponseJson delete(String workId){
        Work work = workService.getWorkById(workId);
        if(work==null){
            return new ResponseJson(ResultCode.EMPTYWORK);
        }
        workService.deleteWorkById(work);
        log.debug("删除文件"+work.getFilename());
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @PostMapping("/deleteBatch")
    @ApiOperation(value = "批量删除作业")
    public ResponseJson deleteBatch(@RequestBody String workId){
        //将json数组转为list
        String workIds = JSONObject.parseObject(workId).getString("workId");
        List<String> ids = JSON.parseArray(workId).toJavaList(String.class);
        boolean allSuccess=true;
        for(String id:ids){
            Work work = workService.getWorkById(id);
            if(work==null){
                allSuccess=false;
                continue;
            }
            try {
                workService.deleteWorkById(work);
                log.debug("删除文件"+work.getFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(allSuccess){
            return new ResponseJson(ResultCode.SUCCESS);
        }
        else{
            return new ResponseJson(ResultCode.EMPTYWORK);
        }
//        System.out.println(filename);

    }
}
