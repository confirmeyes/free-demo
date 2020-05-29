package mail;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * @author WIN10 .
 * @create 2020-05-25-14:54 .
 * @description .
 */

public class SendMailTest {


    public static void main(String[] args) {

        String uuidCode = UUID.randomUUID().toString();
        String code = String.valueOf(reCode4());
        try {
            MailUtil.sendEmail(MailConfig.MAIL_FROM,
                    "614364153@qq.com",
                    MailConfig.MAIL_SUBJECT_REGISTER,
                    MailUtil.prettyQrCodeLayout("24小时", code),
                    MailConfig.MAIL_FROM_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*MailUtil.sendEmail(MailConfig.MAIL_FROM,
                "xxxx@qq.com",
                MailConfig.MAIL_SUBJECT_VERIFY_CODE,
                prettyLayout(MailConfig.MAIL_ORGANIZATION, MailConfig.MAIL_WEBSITE,
                        MailConfig.MAIL_ORGANIZATION_LOGO,
                        MailConfig.prettyQrCodeLayout("2分钟", "580123"),
                        MailConfig.MAIL_ORGANIZATION_QRCODE),
                MailConfig.MAIL_FROM_PASSWORD);
    }*/
    }

    /**
     * 生成6位随机数
     *
     * @return
     */
    public static int reCode4() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }
}
