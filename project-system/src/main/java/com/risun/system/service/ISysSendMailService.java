package com.risun.system.service;

/**
 * 发送邮件服务类
 * 
 * @author dante
 * 
 */
public interface ISysSendMailService {
	
	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，可为空，多个用逗号分割
	 * @param bcc  密送人，可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendMail(String to, String cc, String bcc, String subject, String content);
	
	
	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param cc  抄送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendMail(String to, String cc, String subject, String content);
	
	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param bcc  密送人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendMailWithBcc(String to, String bcc, String subject, String content);
	
	
	/**
	 * 发送邮件
	 * 
	 * @param to  接收人，不可为空，多个用逗号分割
	 * @param subject  主题
	 * @param content  内容
	 */
	public void sendMail(String to, String subject, String content);
}
