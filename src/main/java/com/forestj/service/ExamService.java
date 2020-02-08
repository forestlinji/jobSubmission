package com.forestj.service;

import com.forestj.pojo.Exam;
import com.forestj.pojo.PageResult;

public interface ExamService {
    /**
     * 添加考核
     * @param exam
     */
    public void addExam(Exam exam);

    /**
     * 分页获取考核信息
     * @param page 页码
     * @return
     */
    public PageResult<Exam> getAll(int page);

    /**
     * 根据id查询考核
     * @param examId
     * @return
     */
    public Exam getExamById(String examId);

    /**
     * 更新考核信息
     * @param exam
     */
    public void updateExam(Exam exam);

    /**
     * 删除考核
     * @param examId
     */
    public void deleteById(String examId);
}
