package com.risun.flowable.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.risun.common.core.domain.entity.SysUser;
import com.risun.flowable.domain.SysFlowGroup;

import lombok.Data;

/**
 * 动态人员、组
 * 
 * @author Xuan xuan
 * @date 2021/4/17 22:59
 */
@Data
public class FlowNextDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String type;

    private String vars;

    private List<SysUser> userList;

    private List<SysFlowGroup> groupList;
}
