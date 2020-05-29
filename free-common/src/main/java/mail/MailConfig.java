package mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author WIN10 .
 * @create 2020-05-25-11:41 .
 * @description .
 */

@Component
public class MailConfig {

    //邮件的传输协议
    static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    //使用smtp协议
    static final String MAIL_TRANSPORT_PROTOCOL_VALUE = "smtp";
    //发送邮件的主机
    static final String MAIL_HOST = "mail.host";
    //发送邮件的服务器地址
    static final String MAIL_HOST_VALUE = "smtp.163.com";
    //调试模式
    static final String MAIL_DEBUG = "mail.debug";
    //邮件smtp作者确认
    static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    //确认
    static final String CONFIRM = "true";
    //发送邮件的编码格式
    static final String SEND_ENCODING_LAYOUT = "text/html;charset=utf-8";

    /**
     * 邮件发送人授权码
     */
    static String MAIL_FROM_PASSWORD = "JSKEYDEXNMSJKHPL";
    /**
     * 邮件发送人
     */
    static String MAIL_FROM = "confirmeyes@163.com";
    //邮件主题(注册链接)
    static final String MAIL_SUBJECT_REGISTER = "xxxx【注册链接】";
    //邮件主题(邮箱验证码)
    static final String MAIL_SUBJECT_VERIFY_CODE = "xxxx【邮箱验证码】";
    //邮件组织
    static final String MAIL_ORGANIZATION = "xxxx";
    //组织主页
    static final String MAIL_WEBSITE = "https://www.xxxx.com/";
    //网站logo
    static final String MAIL_ORGANIZATION_LOGO = "https://wwwxxxx.com/xxxx/xxxlogo.jpg";
    //网站二维码图片
    static final String MAIL_ORGANIZATION_QRCODE = "https://www.xxxx.com/xxxx/xxx.jpg";
}
