package com.risun.framework.email;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import com.risun.common.constant.Constants;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysEmailLog;
import com.risun.system.mapper.SysEmailLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件发送工厂
 * 
 * @author dante
 */
@Slf4j
@Component
public class EmailFactory {
	/** 附件文件形式 */
	public static final String ATTACHMENT_FILE = "ATTACHMENT_FILE";
	/** 附件URL形式 */
	public static final String ATTACHMENT_URL = "ATTACHMENT_URL";
	/** 附件ClassPath形式 */
	public static final String ATTACHMENT_CLASSPATH = "ATTACHMENT_CLASSPATH";
	
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
    private SysEmailLogMapper sysEmailLogMapper;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	private static final String SEND_SUCESS = "发送成功";
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 * @param attachmentType	附件类型
	 * @param attachments		附件（可传多个）
	 */
	public void sendMediaMail(String to, String cc, String bcc, String subject, String templatePath, Map<String, Object> dataMap, String attachmentType, String... attachments) {
		Assert.hasText(to, "接收人不可为空");
		Assert.hasText(subject, "主题不可为空");
		String sendLog = SEND_SUCESS;
		SysEmailLog sysEmailLog = buildSysEmailLog(to, cc, bcc, subject, templatePath, sendLog);
        try {
        	MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,
			        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
			        StandardCharsets.UTF_8.name());
			Context context = new Context();
			if(!CollectionUtils.isEmpty(dataMap)) {
				context.setVariables(dataMap);
			}
			helper.setFrom(mailProperties.getUsername());
			helper.setTo(to);
	        helper.setSubject(subject);
			if(StringUtils.hasText(cc)) {
				helper.setCc(cc);
			}
			if(StringUtils.hasText(bcc)) {
				helper.setBcc(bcc);
			}
			if(StringUtils.hasText(templatePath)) {
				String html = templateEngine.process(templatePath, context);
				helper.setText(html, true);
			}
			buildMailAttachment(helper, attachmentType, attachments);
			mailSender.send(message);
		} 
		catch (Exception e) {
			log.error("发送邮件时发生异常！【{}】- 【{}】", to, subject, e);
            sysEmailLog.setSendLog(e.getMessage());
            sysEmailLog.setStatus(Constants.FAIL);
        }
		sysEmailLogMapper.insertSysEmailLog(sysEmailLog);
	}
	
	/**
	 * 发送邮件（异步 - HTML 格式）
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 * @param attachmentType	附件类型
	 * @param attachments		附件（可传多个）
	 */
	@Async("threadPoolTaskExecutor")
	public void sendMediaMailAsync(String to, String cc, String bcc, String subject, String templatePath, Map<String, Object> dataMap, String attachmentType, String... attachments) {
		sendMediaMail(to, cc, bcc, subject, templatePath, dataMap, attachmentType, attachments);
	}
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 * @param attachmentType	附件类型
	 * @param attachments		附件（可传多个）
	 */
	public void sendMediaMail(String to, String subject, String templatePath, Map<String, Object> dataMap, String attachmentType, String... attachments) {
		this.sendMediaMail(to, null, null, subject, templatePath, dataMap, attachmentType, attachments);
	}
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 * @param attachmentType	附件类型
	 * @param attachments		附件（可传多个）
	 */
	public void sendMediaMail(String to, String cc, String subject, String templatePath, Map<String, Object> dataMap, String attachmentType, String... attachments) {
		Assert.hasText(cc, "抄送人不可为空");
		this.sendMediaMail(to, cc, null, subject, templatePath, dataMap, attachmentType, attachments);
	}
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 */
	public void sendMediaMail(String to, String subject, String templatePath, Map<String, Object> dataMap) {
		Assert.hasText(templatePath, "模板路径不能为空");
		this.sendMediaMail(to, null, null, subject, templatePath, dataMap, null, "");
	}
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 */
	public void sendMediaMail(String to, String subject, String attachmentType, String... attachments) {
		Assert.noNullElements(attachments, "附件不能为空");
		this.sendMediaMail(to, null, null, subject, null, null, attachmentType, attachments);
	}
	
	/**
	 * 发送邮件（HTML 格式）
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param templatePath	    模板路径，模板位于 classpath:/template下，这里传递模板文件名称。例如: 模板文件 templates/sms/news.html，则传递 sms/news
	 * @param dataMap	        模板数据
	 * @param attachmentType	附件类型
	 * @param attachments		附件（可传多个）
	 */
	public void sendMediaMailWithBcc(String to, String bcc, String subject, String templatePath, Map<String, Object> dataMap, String attachmentType, String... attachments) {
		Assert.hasText(bcc, "密送人不可为空");
		this.sendMediaMail(to, null, bcc, subject, templatePath, dataMap, attachmentType, attachments);
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，可为空，多个用逗号分割
	 * @param bcc  密送人，可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendSimpleMail(String to, String cc, String bcc, String subject, String content) {
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
            sysEmailLog.setStatus(Constants.FAIL);
        }
		sysEmailLogMapper.insertSysEmailLog(sysEmailLog);
	}
	
	/**
	 * 发送邮件 （异步）
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，可为空，多个用逗号分割
	 * @param bcc  密送人，可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendSimpleMailAsync(String to, String cc, String bcc, String subject, String content) {
		sendSimpleMail(to, cc, bcc, subject, content);
	}

	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendSimpleMail(String to, String cc, String subject, String content) {
		Assert.hasText(cc, "抄送人不可为空");
		this.sendSimpleMail(to, cc, null, subject, content);
	}

	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param bcc  密送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendSimpleMailWithBcc(String to, String bcc, String subject, String content) {
		Assert.hasText(bcc, "密送人不可为空");
		this.sendSimpleMail(to, null, bcc, subject, content);
	}

	/**
	 * 发送邮件 
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendSimpleMail(String to, String subject, String content) {
		this.sendSimpleMail(to, null, null, subject, content);
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
		log.setStatus(Constants.SUCCESS);
		try {
			log.setCreateBy(SecurityUtils.getUsername());
		} catch (Exception e) {
			log.setCreateBy("系统自动");
		}
	    log.setCreateTime(DateUtils.getNowDate());
		return log;
	}
	
	/**
	 * 构造邮件附件
	 * 
	 * @param helper
	 * @param attachmentType
	 * @param attachments
	 * @throws Exception 
	 */
	private void buildMailAttachment(MimeMessageHelper helper, String attachmentType, String... attachments) throws Exception {
		if(ArrayUtil.isNotEmpty(attachments) && StringUtils.hasText(attachmentType)) {
			switch (attachmentType) {
	            case ATTACHMENT_FILE:
	                for (int i = 0; i < attachments.length; i++) {
	                	helper.addAttachment(StringUtils.getFilename(attachments[i]), FileUtil.file(attachments[i]));
					}
	                break;
            	case ATTACHMENT_URL:
            		for (int i = 0; i < attachments.length; i++) {
	                	helper.addAttachment(StringUtils.getFilename(attachments[i]), new UrlResource(attachments[i]));
					}
	                break;
            	case ATTACHMENT_CLASSPATH:
            		for (int i = 0; i < attachments.length; i++) {
	                	helper.addAttachment(StringUtils.getFilename(attachments[i]), new ClassPathResource(attachments[i]));
					}
	                break;
            	default:
                    throw new Exception("附件类型错误！");
			}
		}
	}
}
