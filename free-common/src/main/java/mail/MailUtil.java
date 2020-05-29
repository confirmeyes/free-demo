package mail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author WIN10 .
 * @create 2020-05-25-14:44 .
 * @description .
 */
public class MailUtil {

    /**
     * 发送邮件
     *
     * @param from     发送人(邮箱地址)
     * @param to       接收人  (邮箱地址)
     * @param subject  主题
     * @param content  内容
     * @param password 密码(密码为授权码不是邮箱的登录密码)
     * @throws Exception
     */
    public static void sendEmail(String from, String to, String subject, String content, String password) throws Exception {
        //key value:配置参数。真正发送邮件时再配置
        Properties props = new Properties();
        //指定邮件发送的协议，参数是规范规定的
        props.setProperty(MailConfig.MAIL_TRANSPORT_PROTOCOL, MailConfig.MAIL_TRANSPORT_PROTOCOL_VALUE);
        //指定发件服务器的地址，参数是规范规定的
        props.setProperty(MailConfig.MAIL_HOST, MailConfig.MAIL_HOST_VALUE);
//        props.setProperty(MAIL_DEBUG, "true");//邮件发送的调试模式，参数是规范规定的
        //请求服务器进行身份认证。参数与具体的JavaMail实现有关
        props.setProperty(MailConfig.MAIL_SMTP_AUTH, MailConfig.CONFIRM);
        //发送邮件时使用的环境配置
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        //设置邮件的头
        message.setFrom(new InternetAddress(from));
        //指定发给谁
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);
        //设置正文
        message.setContent(content, MailConfig.SEND_ENCODING_LAYOUT);
//        message.setText("<h1>hello</h1>");//纯文本
        message.saveChanges();

        //发送邮件
        Transport ts = session.getTransport();
        // 密码为授权码不是邮箱的登录密码
        ts.connect(from, password);
        ts.sendMessage(message, message.getAllRecipients());
    }


    /**
     * 发送邮箱验证码
     *
     * @param validity   有效期/单位自行填写
     * @param verifyCode 验证码
     * @return
     */
    public static String prettyQrCodeLayout(String validity, String verifyCode) {
        return "<div>邮箱验证码的有效期为:" + validity + "邮箱验证码如下:</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"font-size:\n" +
                "12px; line-height: 20px; padding-top: 14px;\n" +
                "padding-bottom: 25px; color: #909090;\">\n" +
                "                    <div>" + verifyCode + "</div>\n" +
                "                </td>\n" +
                "            </tr>\n";
    }


    /**
     * 发送邮箱注册激活链接
     *
     * @param validity    有效期/单位自行填写
     * @param websiteName 网站名
     * @param verifyLink  注册验证链接
     * @return
     */
    private static String prettyRegisterLayout(String validity, String websiteName, String verifyLink) {
        return "<div>请点击以下的链接验证您的邮箱，验证成功后就可以使用" + websiteName + "提供的服务了。</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"font-size:\n" +
                "12px; line-height: 20px; padding-top: 14px;\n" +
                "padding-bottom: 25px; color: #909090;\">\n" +
                "                    <div>该链接的有效期为" + validity + "，如链接超过有效期请重新发送邮件<a href=\"" + verifyLink + "\" style=\"color: #03c5ff; text-decoration:\n" +
                "underline;\" rel=\"noopener\" target=\"_blank\">" + verifyLink + "</a></div>\n" +
                "                    <div style=\"padding-top:\n" +
                "4px;\">(如果不能打开页面，请复制该地址到浏览器打开)</div>\n" +
                "                </td>\n" +
                "            </tr>\n";
    }


    /**
     * 邮件内容头部
     *
     * @param websiteName    网站名称
     * @param principal      网站主页
     * @param principalImage 网站logo图片超链接
     * @param logoWidth      logo图片宽度
     * @param logoHeight     logo图片高度
     * @return
     */
    private static String mailHead(String websiteName, String principal, String principalImage, String logoWidth, String logoHeight) {
        return "<div style=\"margin: 0 auto; text-align: left;\n" +
                "padding: 38px 50px; width: 560px; font-size: 14px;\n" +
                "color: #606060; background: #fff; border-radius:\n" +
                "2px; font-family:Tahoma,Helvetica,'microsoft\n" +
                "yahei','Hiragino Sans GB',Simsun,sans-serif;\n" +
                "box-shadow: 0 0 4px rgba(0,0,0,0.2);\">\n" +
                "        <table style=\"margin: 0 auto;\n" +
                "text-align: left; font-size: 14px; color: #606060;\n" +
                "background: #fff; font-family: inherit;\n" +
                "font-family:Tahoma,Helvetica,'microsoft\n" +
                "yahei','Hiragino Sans GB',Simsun,sans-serif;\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "            <tbody><tr>\n" +
                "                <th colspan=\"2\"><a href=\"" + principal + "\" rel=\"noopener\" target=\"_blank\"><img src=\"" + principalImage + "\" width=\"" + logoWidth + "\" height=\"" + logoHeight + "\"></a></th>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"font-size:\n" +
                "18px; padding: 30px 0\n" +
                "18px;\">尊敬的" + websiteName + "用户：</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"line-height: 1.8;\">\n" +
                "                   \n" +
                "<div>欢迎使用" + websiteName + "！</div>\n";
    }

    /**
     * 邮件内容脚部
     *
     * @param websiteName 网站名称
     * @param principal   网站主页
     * @param qrCode      二维码图片链接(可不填写/仅支持第一张)
     * @return
     */
    private static String mailFoot(String websiteName, String principal, String... qrCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String nowDate = sdf.format(new Date());
        return "            <tr>\n" +
                "                <td colspan=\"2\" style=\"text-align:\n" +
                "right; line-height: 1.8; padding-bottom: 18px;\">\n" +
                "                    <div>" + websiteName + "团队</div>\n" +
                "                    <div style=\"color:\n" +
                "#909090;\">" + nowDate + "</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                (qrCode != null && qrCode.length != 0 ? setQrCode(websiteName, qrCode[0]) : "") +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"padding-top: 20px; border-top: 1px solid\n" +
                "#e7e7e7; line-height: 1.8; font-size: 12px; color:\n" +
                "#909090;\">\n" +
                "                    <div>温馨提示：</div>\n" +
                "                    <div>1.\n" + websiteName +
                "官方网址为：<a href=\"" + principal + "\" rel=\"noopener\" target=\"_blank\">" + principal + "</a>，请注意网址，防止钓鱼。</div>\n" +
                "                    <div>2.\n" +
                "本邮件为系统自动发出，请勿回复。</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody></table>\n" +
                "    </div>";
    }

    /**
     * 输出漂亮格式(带logo,可带二维码)
     *
     * @param websiteName    网站名称
     * @param principal      主页地址
     * @param principalImage 主页logo 323 x 48 拉伸宽度为 (超链接形式)
     * @param business       业务
     * @param qrCode         二维码 (超链接形式)
     * @return
     */
    private static String prettyLayout(String websiteName, String principal, String principalImage, String business, String... qrCode) {
        return mailHead(websiteName, principal, principalImage, "", "") +
                business +
                mailFoot(websiteName, principal, qrCode);


    }


    /**
     * 二维码 样式A
     *
     * @param websiteName //网站名称
     * @param qrCode      //二维码图片链接
     * @return
     */
    private static String setQrCode(String websiteName, String qrCode) {
        return "            <tr>\n" +
                "                <td style=\"padding-bottom: 20px;\" width=\"145\">\n" +
                "                    <img src=\"" + qrCode + "\" width=\"110\" height=\"112\">\n" +
                "                </td>\n" +
                "                <td style=\"line-height: 2;\n" +
                "padding-bottom: 20px;\">\n" +
                "                    <div style=\"font-size: 18px;\n" +
                "color:\n" +
                "#03c5ff;\">扫描关注" + websiteName + "公众号</div>\n" +
                "                </td>\n" +
                "            </tr>\n";
    }
}
