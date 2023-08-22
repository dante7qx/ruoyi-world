package com.risun.biz.demo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 业务对象 t_demo
 * 
 * @author sunchao
 * @date 2023-05-04
 */
@TableName("t_demo")
public class Demo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 业务主键ID */
    @TableId(type = IdType.AUTO)
    private Long demoId;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private String demoName;

    /** 业务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "业务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date demoTime;

    /** 业务图片 */
    private String demoImage;

    /** 业务附件 */
    private String attachment;

    /** 业务内容 */
    @Excel(name = "业务内容")
    private String demoContent;

    /** 删除标识 0 未删除 1 已删除 */
    private Integer delFlag;

    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public Long getDemoId() {
        return demoId;
    }
    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDemoName() {
        return demoName;
    }
    public void setDemoTime(Date demoTime) {
        this.demoTime = demoTime;
    }

    public Date getDemoTime() {
        return demoTime;
    }
    public void setDemoImage(String demoImage) {
        this.demoImage = demoImage;
    }

    public String getDemoImage() {
        return demoImage;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachment() {
        return attachment;
    }
    public void setDemoContent(String demoContent) {
        this.demoContent = demoContent;
    }

    public String getDemoContent() {
        return demoContent;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("demoId", getDemoId())
            .append("demoName", getDemoName())
            .append("demoTime", getDemoTime())
            .append("demoImage", getDemoImage())
            .append("attachment", getAttachment())
            .append("demoContent", getDemoContent())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
