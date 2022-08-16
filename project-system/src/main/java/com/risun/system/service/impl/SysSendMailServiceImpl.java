package com.risun.system.service.impl;

import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysEmailLog;
import com.risun.system.mapper.SysEmailLogMapper;
import com.risun.system.service.ISysSendMailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送邮件服务实现类
 * 
 * @author dante
 */
@Slf4j
@Service
public class SysSendMailServiceImpl implements ISysSendMailService {
	
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
    private SysEmailLogMapper sysEmailLogMapper;
	
	private static final String SEND_SUCESS = "发送成功";

	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，可为空，多个用逗号分割
	 * @param bcc  密送人，可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	@Override
	public void sendMail(String to, String cc, String bcc, String subject, String content) {
		SimpleMailMessage message = buildMailMessage(to, subject);
		if(StringUtils.hasText(cc)) {
			message.setCc(cc);
		}
		if(StringUtils.hasText(bcc)) {
			message.setBcc(bcc);
		}
		if(StringUtils.hasText(content)) {
			message.setText(content);
		}
		String sendLog = SEND_SUCESS;
		SysEmailLog sysEmailLog = buildSysEmailLog(to, cc, bcc, subject, content, sendLog);
		try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送邮件时发生异常！【{}】- 【{}】", to, subject, e);
            sysEmailLog.setSendLog(e.getMessage());
        }
		sysEmailLogMapper.insertSysEmailLog(sysEmailLog);
	}

	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	@Override
	public void sendMail(String to, String cc, String subject, String content) {
		this.sendMail(to, cc, null, subject, content);
	}

	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param bcc  密送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	@Override
	public void sendMailWithBcc(String to, String bcc, String subject, String content) {
		this.sendMail(to, null, bcc, subject, content);
	}

	/**
	 * 发送邮件 
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	@Override
	public void sendMail(String to, String subject, String content) {
		this.sendMail(to, null, null, subject, content);
	}
	
	/**
	 * 构造邮件实体
	 * 
	 * @param to
	 * @param subject
	 * @return
	 */
	private SimpleMailMessage buildMailMessage(String to, String subject) {
		Assert.hasText(to, "接收人不可为空");
		Assert.hasText(subject, "主题不可为空");
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        return message;
	}
	
	/**
	 * 构造邮件日志
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 * @return
	 */
	private SysEmailLog buildSysEmailLog(String to, String cc, String bcc, String subject, String content, String sendlog) {
		SysEmailLog log = new SysEmailLog();
		log.setSendTo(to);
		log.setSendCc(bcc);
		log.setSendBcc(bcc);
		log.setSubject(subject);
		log.setContent(content);
		log.setSendDate(DateUtils.getNowDate());
		log.setSendLog(sendlog);
		try {
			log.setCreateBy(SecurityUtils.getUsername());
		} catch (Exception e) {
			log.setCreateBy("系统自动");
		}
	    log.setCreateTime(DateUtils.getNowDate());
		return log;
	}

}
