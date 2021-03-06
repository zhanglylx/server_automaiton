package server_automaiton_gather.server_automaiton_Utils;

import com.mfeia.book.server_automaiton.Test;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import static server_automaiton_gather.server_automaiton_Utils.HtmlUtils.getExecutiveOutcomeslogFile;

public class MailUtils {
    //发件人地址
    private static String senderAddress;
    //收件人地址
    private static InternetAddress[] recipientAddress;
    //发件人账户名
    private static String senderAccount;
    //发件人账户密码
    private static String senderPassword;
    private static final String CHARSET = "UTF-8";
    private static Properties props;
    private static final long fileMaxSize =45*1024*1024L;

    static {
        try {
            Properties properties = PropertiesConfig.getPropertiesConfig(PropertiesConfig.MAIL_CONFIG);
            recipientAddress = new InternetAddress[]{};
            String[] addressees = properties.getProperty("addressee")
                    .trim()
                    .replace("，", ",")
                    .split(",");
            for (String address : addressees) {
                recipientAddress = Arrays.copyOf(recipientAddress, recipientAddress.length + 1);
                recipientAddress[recipientAddress.length - 1] = new InternetAddress(address.trim());
            }
            senderAddress = properties.getProperty("senderAddress").trim();
            senderAccount = properties.getProperty("senderAccount").trim();
            senderPassword = properties.getProperty("senderPassword").trim();
            props = new Properties();
            //设置用户的认证方式
            props.setProperty("mail.smtp.auth", properties.getProperty("mail.smtp.auth").trim());
            //设置传输协议
            props.setProperty("mail.transport.protocol", properties.getProperty("mail.transport.protocol").trim());
            //设置发件人的SMTP服务器地址
            props.setProperty("mail.smtp.host", properties.getProperty("mail.smtp.host").trim());

        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(MailUtils.class, "load", e));
            e.printStackTrace();
        }
    }


    public static boolean sendMail(List<File> files, String title, String content) {
        Transport transport = null;
        try {
            //2、创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getInstance(props);
            //设置调试信息在控制台打印出来
            session.setDebug(false);
            //3、创建邮件的实例对象
            Message msg = getMimeMessage(session, files, title, content);
            //4、根据session对象获取邮件传输对象Transport
            transport = session.getTransport();
            //设置发件人的账户名和密码
            transport.connect(senderAccount, senderPassword);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg, msg.getAllRecipients());


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //5、关闭邮件连接
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    private static MimeMessage getMimeMessage(Session session, List<File> files, String title, String content) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        // 设置文本和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.setSubType("mixed");         // 混合关系
        //设置邮件主题
        msg.setSubject(title, CHARSET);
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
        MimeBodyPart mainBody = new MimeBodyPart();
        mimeMultipart.addBodyPart(mainBody);
        if (null != files) {
            StringBuilder contentBuilder = new StringBuilder(content);
            for (File file : files) {
                if (!file.exists()) {
                    contentBuilder.append(HtmlUtils.getErrInfo("文件不存在:" + file.getPath(), 4));
                    continue;
                } else if (!file.isFile()) {
                    contentBuilder.append(HtmlUtils.getErrInfo("不是一个文件:" + file.getPath(), 4));
                    continue;
                } else if (file.length() > fileMaxSize) {
                    contentBuilder.append(HtmlUtils.getErrInfo("文件超过45MB,未加入附件:" + file.getPath(), 4));
                    continue;
                }
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dh2 = new DataHandler(new FileDataSource(file));
                attachment.setDataHandler(dh2);
                attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
                mimeMultipart.addBodyPart(attachment);
            }
            content = contentBuilder.toString();
        }
        mainBody.setContent(content, "text/html;charset=" + CHARSET);
        // 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mimeMultipart);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        msg.saveChanges();
        return msg;
    }

}