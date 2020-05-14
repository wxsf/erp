package com.example.demo.base.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author ： xu gaoxiang
 */
@Setter
@Getter
public class OperationLog {
    /** 主键 **/
    private Long id;
    /** 操作者 **/
    private int operatorId;
    /** 操作描述 **/
    private String operateType;
    /** 操作内容 **/
    private String content;
    /** ip **/
    private String ip;
    /** 操作时间 **/
    private LocalDateTime operatorTime;
    /** 异常信息 **/
    private String exception;
    /** 创建时间 **/
    private LocalDateTime createTime;
    /** 修改时间 **/
    private LocalDateTime updateTime;
}
