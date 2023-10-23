package com.spirit.flowable.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author XuanXuan
 * @date 2021/3/31 23:20
 */
@Data
public class FlowFromFieldDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
	private Object fields;
}
