package com.forestj.pojo;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode {
    SUCCESS(200,"成功"),
    ERROR(500,"服务器繁忙"),
    ERRORMETHOD(501,"接口调用方式错误"),
    //用户模块
    EMPTYINFO(201,"用户名或密码为空"),
    WRONGINFO(202,"用户名或密码错误"),
    UNAUTHENTICATED(203,"未登录"),
    WRONGPWD(204,"旧密码错误"),       //修改密码时旧密码错误
    EMPTYPWD(205,"id,旧密码或新密码为空"),       //修改密码时新密码为空
    REITERATEDID(206,"账号重复"),
    WRONGINVITATIONCODE(207,"邀请码错误"),
    //考核模块
    WRONGEXAMPAGE(301,"页码错误"),
    EMPTYEXAM(302,"考核不存在"),
    //作业模块
    WRONGFORMAT(601,"文件格式错误"),
    BIGFILE(602,"文件过大"),
    UPDATED(603,"提交且修改成功"),
    WRONGWORKPAGE(604,"页码错误"),
    EMPTYWORK(605,"作业不存在"),
    WRONGSTATE(606,"作业状态错误"),
    OUTOFTIME(607,"考核已经截止");
    //操作代码
    int code;
    //提示信息
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
