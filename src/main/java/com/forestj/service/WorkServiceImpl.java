package com.forestj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.WorkMapper;
import com.forestj.pojo.PageResult;
import com.forestj.pojo.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService{

    @Autowired
    private WorkMapper workMapper;

    @Override
    public Work selectWorkByNameAndId(String name, String stuId) {
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name).eq("stu_id",stuId);
        Work work = workMapper.selectOne(wrapper);
        return work;
    }

    @Override
    public void deleteWorkById(Work work) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(new File("workFile/"+work.getExamId(),work.getFilename()));
            workMapper.deleteById(work.getWorkId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWork(Work work,boolean exist) {
        if(exist){
            workMapper.updateById(work);
        }
        else {
            workMapper.insert(work);
        }
    }

    @Override
    public PageResult<Work> getAllWorks(String examId, int page) {
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id",examId);
        IPage<Work> iPage = workMapper.selectPage(new Page<>(page,10), wrapper);
        PageResult<Work> pageResult = new PageResult<>();
        pageResult.setCurrent(iPage.getCurrent());
        pageResult.setRecords(iPage.getRecords());
        pageResult.setTotal((iPage.getTotal()%10==0)?iPage.getTotal()/10:iPage.getTotal()/10+1);
        pageResult.setCount(pageResult.getRecords().size());
        return pageResult;
    }

    @Override
    public List<Work> getWorks(String examId) {
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id",examId);
        List<Work> works = workMapper.selectList(wrapper);
        return works;
    }

    public Work getWorkById(String workId){
        Work work = workMapper.selectById(workId);
        return work;
    }
}
