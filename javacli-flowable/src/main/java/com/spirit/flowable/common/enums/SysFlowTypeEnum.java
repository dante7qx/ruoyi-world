package com.spirit.flowable.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统流程类型
 * 
 * @author dante
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SysFlowTypeEnum {
	FLOW_DEMO("leave", "请假");

	private final String type;
	private final String desc;

	public static SysFlowTypeEnum fromType(String type) {
		for (SysFlowTypeEnum statusEnum : SysFlowTypeEnum.values()) {
			if (statusEnum.getType().equals(type)) {
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("无效的流程类型: " + type);
	}

	@Override
	public String toString() {
		return this.name() + "[" + this.type + "," + this.desc + "]";
	}

}
