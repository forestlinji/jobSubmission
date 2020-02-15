package com.forestj.service;

import com.forestj.pojo.PageResult;
import com.forestj.pojo.Work;

import java.util.List;

public interface WorkService {
    /**
     * 通过学号和姓名检索作业
     * @param name
     * @param stuId
     * @return
     */
    public Work selectWorkByNameAndId(String name,String stuId);

    /**
     * 删除作业
     * @param work
     */
    public void deleteWorkById(Work work);

    /**
     * 添加作业
     * @param work
     * @param exist 作业是否存在
     */
    public void addWork(Work work,boolean exist);

    /**
     * 分页查询某个考核对应作业
     * @param examId
     * @param page
     * @return
     */
    public PageResult<Work> getAllWorks(String examId,int page);

    /**
     * 获得某考核所有作业
     * @param examId
     * @return
     */
    public List<Work> getWorks(String examId);

    /**
     * 根据作业id查询作业
     * @param workId
     * @return
     */
    public Work getWorkById(String workId);
}
