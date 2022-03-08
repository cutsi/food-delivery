package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailService {
    private final TokenService tokenService;
    private final UserRepo userRepo;
    private final JavaMailSender mailSender;
    private static final String USER_NOT_FOUND = "Korisnik sa emailom: %s nije pronađen";
    private static final String FROM_ADDRESS = "josipcutura1997@gmail.com";
    private static final String SENDER_NAME = "MEZI";
    private static final String REGISTRATION_SUBJECT = "Potvrdite svoju registraciju";
    private static final String CHANGE_PASSWORD_SUBJECT = "Promijenite svoju lozinku";
    private static final String CHANGE_PASSWORD_URL = "mezi.online/promijeni-lozinku?code=";
    private static final String CONFIRM_EMAIL_URL = "mezi.online/verify?code=";
    private static final String CONFIRM_EMAIL_HEADER = "Dobrodošli!";
    private static final String CHANGE_PASSWORD_HEADER = "Pozdrav";
    private static final String CONFIRM_EMAIL_BODY = "Sve što trebate napraviti je kliknuti na gumb ispod kako bi se verificirali.";
    private static final String CHANGE_PASSWORD_BODY = "Izgleda da ste zaboravili svoju lozinku. Nikakav problem. Samo kliknite na gumb ispod kako bi je promijenili";
    private static final String BUTTON_EMAIL_CONFIRMATION_TEXT = "Potvrdite račun";
    private static final String BUTTON_PASSWORD_CHANGE_TEXT = "Promijenite šifru";
    private static final String CONFIRM_EMAIL_IMG = "https://img.icons8.com/clouds/100/000000/handshake.png";
    private static final String PASSWORD_CHANGE_IMG = "https://i.imgur.com/247tYSw.png";


    public void sendPasswordChangeEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String email = buildEmail(getPasswordChangeCode
                (tokenService.getTokenByUser(user).get().getToken()),user.getName(),
                CHANGE_PASSWORD_HEADER, CHANGE_PASSWORD_BODY,BUTTON_PASSWORD_CHANGE_TEXT, PASSWORD_CHANGE_IMG);
        sendEmail(toAddress, CHANGE_PASSWORD_SUBJECT, email);
    }
    public void sendRegistrationConfirmEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String email = buildEmail(getRegistrationCode
                        (tokenService.getTokenByUser(user).get().getToken()),user.getName(),
                CONFIRM_EMAIL_HEADER, CONFIRM_EMAIL_BODY,BUTTON_EMAIL_CONFIRMATION_TEXT, CONFIRM_EMAIL_IMG);
        sendEmail(toAddress, REGISTRATION_SUBJECT, email);
    }
    private void sendEmail(String toAddress, String subject,String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(FROM_ADDRESS, SENDER_NAME);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(email, true);

        mailSender.send(message);
    }
    private String getPasswordChangeCode(String code){
        return CHANGE_PASSWORD_URL + code;
    }
    private String getRegistrationCode(String code){
        return CONFIRM_EMAIL_URL + code;
    }
    public boolean verify(String verificationCode) {
        Optional<User> userOptional = userRepo.findById(tokenService.getTokenByCode(verificationCode).get().getUser().getId());
        if (!userOptional.isPresent() || !tokenService.getTokenByCode(verificationCode).isPresent())
            return false;
        if (userOptional.get().isEnabled()){
            return false;
        }
        else {
            User user = userOptional.get();
            tokenService.deleteToken(tokenService.getTokenByCode(verificationCode).get());
            user.setIsEnabled(true);
            userRepo.save(user);
            return true;
        }
    }
    private String buildEmail(String verification_link, String user_name, String header, String body, String buttonText, String imgSrc){
        String message = "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <title>\n" +
                "\n" +
                "    </title>\n" +
                "    <!--[if !mso]><!-- -->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <!--<![endif]-->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <style type=\"text/css\">\n" +
                "        #outlook a {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .ReadMsgBody {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass * {\n" +
                "            line-height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        td {\n" +
                "            border-collapse: collapse;\n" +
                "            mso-table-lspace: 0pt;\n" +
                "            mso-table-rspace: 0pt;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            border: 0;\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            display: block;\n" +
                "            margin: 13px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <style type=\"text/css\">\n" +
                "        @media only screen and (max-width:480px) {\n" +
                "            @-ms-viewport {\n" +
                "                width: 320px;\n" +
                "            }\n" +
                "            @viewport {\n" +
                "                width: 320px;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        @media only screen and (min-width:480px) {\n" +
                "            .mj-column-per-100 {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color:#f9f9f9;\">\n" +
                "\n" +
                "\n" +
                "    <div style=\"background-color:#f9f9f9;\">\n" +
                "\n" +
                "        <div style=\"background:#f9f9f9;background-color:#f9f9f9;Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#f9f9f9;background-color:#f9f9f9;width:100%;\">\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td style=\"border-bottom:#333957 solid 5px;direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\n" +
                "                            <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"background:#fff;background-color:#fff;Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#fff;background-color:#fff;width:100%;\">\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td style=\"border:#dddddd solid 1px;border-top:0px;direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\n" +
                "\n" +
                "\n" +
                "                            <div class=\"mj-column-per-100 outlook-group-fix\" style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:bottom;width:100%;\">\n" +
                "\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:bottom;\" width=\"100%\">\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                <tbody>\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"width:64px;\">\n" +
                "\n" +
                "\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </tbody>\n" +
                "                                            </table>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:40px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:38px;font-weight:bold;line-height:1;text-align:center;color:#555;\">\n" +
                "                                                [header] [user_name]\n" +
                "                                            </div>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:40px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:18px;line-height:1;text-align:center;color:#555;\">\n" +
                "                                                [body]\n" +
                "                                            </div>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                <tbody>\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"width:128px;\">\n" +
                "\n" +
                "                                                            <img height=\"auto\" src=\"[img_src]\" style=\"border:0;display:block;outline:none;text-decoration:none;width:100%;\" width=\"128\" />\n" +
                "\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </tbody>\n" +
                "                                            </table>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-top:30px;padding-bottom:50px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;line-height:100%;\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" bgcolor=\"#2F67F6\" role=\"presentation\" style=\"border:none;border-radius:3px;color:#ffffff;cursor:auto;padding:15px 25px;\" valign=\"middle\">\n" +
                "                                                    <a href='[[verification_link]]' target='_self'>\n" +
                "                                                        <p style=\"background:#2F67F6;color:#ffffff;font-family:'Helvetica Neue',Arial,sans-serif;font-size:15px;font-weight:normal;line-height:120%;Margin:0;text-decoration:none;text-transform:none;\">\n" +
                "                                                            [button_text]\n" +
                "                                                        </p>\n" +
                "                                                    </a>" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:40px;word-break:break-word;\">\n" +
                "\n" +
                "                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:16px;line-height:20px;text-align:center;color:#7F8FA4;\">\n" +
                "                                                Ukoliko gumb ne radi, kopirajte ovaj link i zalijepite ga u svoj internet pretraživač:\n" + "<br><br>" +
                "                                                   <a href=\"[[verification_link]]\" target=\"_blank\" style=\"color: darkred;\">[[verification_link]]</a></p>\n" +
                "                                            </div>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "\n" +
                "                                </table>\n" +
                "\n" +
                "                            </div>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\n" +
                "\n" +
                "                            <div class=\"mj-column-per-100 outlook-group-fix\" style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:bottom;width:100%;\">\n" +
                "\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"vertical-align:bottom;padding:0;\">\n" +
                "\n" +
                "                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                "\n" +
                "                                                    <tr>\n" +
                "                                                        <td align=\"center\" style=\"font-size:0px;padding:0;word-break:break-word;\">\n" +
                "\n" +
                "                                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:12px;font-weight:300;line-height:1;text-align:center;color:#575757;\">\n" +
                "                                                                Ukoliko imate nekih problema\n" +
                "                                                            </div>\n" +
                "\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "\n" +
                "                                                    <tr>\n" +
                "                                                        <td align=\"center\" style=\"font-size:0px;padding:10px;word-break:break-word;\">\n" +
                "\n" +
                "                                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:12px;font-weight:300;line-height:1;text-align:center;color:#575757;\">\n" +
                "                                                                <a href='mezi.online/Kontaktirajte-nas'>Kontaktirajte nas</a>" +
                "                                                            </div>\n" +
                "\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "\n" +
                "                                                </table>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "\n" +
                "                            </div>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        message = message.replace("[[verification_link]]", verification_link); //header, body, buttonText
        message = message.replace("[header]", header);
        message = message.replace("[user_name]", user_name);
        message = message.replace("[body]", body);
        message = message.replace("[button_text]", buttonText);
        message = message.replace("[img_src]", imgSrc);
        return message;

    }
}
