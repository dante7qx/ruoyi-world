package com.spirit.flowable.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.flowable.domain.SysFlowSeq;
import com.spirit.flowable.mapper.SysFlowSeqMapper;
import com.spirit.flowable.service.ISysFlowSeqService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/**
 * 流程序号Service业务层处理
 * 
 * @author sunchao
 * @date 2023-02-27
 */
@Service
public class SysFlowSeqServiceImpl implements ISysFlowSeqService {
	
	@Autowired
	private SysFlowSeqMapper sysFlowSeqMapper;

	/**
	 * 获取最新的流程序号
	 * 
	 */
	@Override
	public SysFlowSeq selectNextSysFlowSeq() {
		Long seqNum = 1L;
		String seqDateNum = DateUtil.format(Date.from(Instant.now()), DatePattern.PURE_DATE_PATTERN);
		SysFlowSeq seq = null;
		List<SysFlowSeq> seqs = sysFlowSeqMapper.selectSysFlowSeqList(new SysFlowSeq());
		if (CollUtil.isEmpty(seqs)) {
			seq = new SysFlowSeq();
			seq.setSeqNum(seqNum);
			seq.setDateNum(Long.parseLong(seqDateNum + "000001"));
			sysFlowSeqMapper.insertSysFlowSeq(seq);
		} else {
			seq = seqs.get(0);
			seqNum = seq.getSeqNum() + 1;
			seq.setSeqNum(seqNum);
			String dateNumStr = seq.getDateNum()
				.toString();
			String datePrefix = dateNumStr.substring(0, 8);
			Long num = Long.parseLong(dateNumStr.substring(8, dateNumStr.length()));
			if (seqDateNum.equals(datePrefix)) {
				String nextNum = (num + 1) + "";
				int index = nextNum.length() + 1;
				for (int i = index; i <= 6; i++) {
					nextNum = "0" + nextNum;
				}
				seq.setDateNum(Long.parseLong(datePrefix + nextNum));
			} else {
				seq.setDateNum(Long.parseLong(seqDateNum + "000001"));
			}
			seq.setUpdateTime(Date.from(Instant.now()));
			sysFlowSeqMapper.updateSysFlowSeq(seq);
		}
		return seq;
	}

}
