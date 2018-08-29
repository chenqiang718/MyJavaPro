package cq.common;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * 发送邮件工具
 * 
 * @author: 尚正平(shangzhengping@adtime.com)
 * @date: 2017年4月28日 上午10:17:11
 * @Copyright: 2016 浙江德嘉信息技术有限公司 All Rights Reserved
 */
public class EmailUtils {

	private static final String SEND_USER = "chenqiang718@sina.com";
	private static final String SEND_PWD = "18232582";


	public static final void sendEmail(String[] email, String title, String context) throws UnsupportedEncodingException, MessagingException {
		String VALUE_SMTP = "smtp.sina.com.cn";
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", VALUE_SMTP);
		props.setProperty("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_USER, SEND_PWD);
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(SEND_USER, title));
		InternetAddress[] tmp = new InternetAddress[email.length];
		for (int i = 0; i < email.length; i++) {
			tmp[i] = new InternetAddress(email[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, tmp);
		msg.setSubject(title);
		msg.setSentDate(new Date());
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(context, "text/html;charset=gbk");
		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		msg.setContent(mp);
		msg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(SEND_USER));
		Transport transport = session.getTransport("smtp");
		transport.connect(VALUE_SMTP, SEND_USER, SEND_PWD);
		Transport.send(msg);
		transport.close();
	}
}
