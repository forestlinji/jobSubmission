package com.forestj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.ExamMapper;
import com.forestj.pojo.Exam;
import com.forestj.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService{
    @Autowired
    private ExamMapper examMapper;

    @Override
    @CacheEvict
    public void addExam(Exam exam) {
        examMapper.insert(exam);
        IPage<Exam> page=new Page<>(1,1);
    }

    @Override
    @Cacheable(cacheNames = "workPages")
    public PageResult<Exam> getAll(int page) {
        IPage<Exam> iPage = examMapper.selectPage(new Page<>(page,10), null);
        PageResult<Exam> pageResult = new PageResult<>();
        pageResult.setCurrent(iPage.getCurrent());
        pageResult.setRecords(iPage.getRecords());
        pageResult.setTotal((iPage.getTotal()%10==0)?iPage.getTotal()/10:iPage.getTotal()/10+1);
        pageResult.setCount(pageResult.getRecords().size());
        return pageResult;
    }

    @Override
    @Cacheable(cacheNames = "work")
    public Exam getExamById(String examId) {
        return examMapper.selectById(examId);
    }

    @Override
    @CacheEvict
    public void updateExam(Exam exam) {
        examMapper.updateById(exam);
    }

    @Override
    @CacheEvict
    public void deleteById(String examId) {
        examMapper.deleteById(examId);
    }
}
