package com.risun.flowable.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统流程状态
 * 
 * @author dante
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SysFlowStatusEnum {
	TO_BE_COMMIT(1, "待提交"), 
	IN_PROGRESS(2, "审批中"), 
	FINISHED(3, "完成");

	private final int type;
	private final String desc;

	public static SysFlowStatusEnum fromType(int type) {
		for (SysFlowStatusEnum statusEnum : SysFlowStatusEnum.values()) {
			if (statusEnum.getType() == type) {
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("无效的流程状态: " + type);
	}

	@Override
	public String toString() {
		return this.name() + "[" + this.type + "," + this.desc + "]";
	}

}
