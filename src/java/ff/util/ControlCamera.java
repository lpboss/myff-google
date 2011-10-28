package ff.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.log4j.Logger;

/**
 *作者：joey
 *描述：控制摄像机的功能实现类，被service调用。注意：不要被controller直接调用。本类应该写在dao中。
 */
public class ControlCamera {

    static Logger logger = Logger.getLogger(ControlCamera.class.getName());

    public ControlCamera() {
        super();
    }
    String username = "", password = "";

    public void setUsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    class MyAuthenticator {

        String getPasswordAuthentication() {
            return username + ":" + password;
        }
    }

    /**
     *作者：joey
     *描述：通过输入的url参数，并附加上用户名与密码，进行url访问。实现对摄像机的控制
     */
    public void getUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        // Popup Window to request username/password password
        MyAuthenticator ma = new MyAuthenticator();
        String userPassword = ma.getPasswordAuthentication();
        // Encode String
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        URLConnection uc = url.openConnection();
        uc.setRequestProperty("Authorization", "Basic " + encoding);
        String myCookie = "ptz_ctl_id=225112";
        uc.setRequestProperty("Cookie", myCookie);
        InputStream content = (InputStream) uc.getInputStream();                //触发访问url的操作
    }

    /**
     *作者：joey
     *描述：通过输入的url参数，并附加上用户名与密码，进行url访问。将访问得到的输出流进行返回。读取多个流，失效快，用于刷新列表用
     */
    public String getInputStreamFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        // Popup Window to request username/password password
        MyAuthenticator ma = new MyAuthenticator();
        String userPassword = ma.getPasswordAuthentication();
        // Encode String
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        URLConnection uc = url.openConnection();
        uc.setConnectTimeout(10);
        uc.setReadTimeout(10);
        uc.setRequestProperty("Authorization", "Basic " + encoding);
        String myCookie = "ptz_ctl_id=225112";
        uc.setRequestProperty("Cookie", myCookie);
        InputStream content = (InputStream) uc.getInputStream();                //触发访问url的操作
        if (content != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(content));
                StringBuilder tStringBuffer = new StringBuilder();
                String sTempOneLine = "";
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();                                //返回String
            } catch (Exception ex) {
                logger.info(ex);
                return ex.toString();                                           //返回异常信息
            }
        }
        return "null";                                                          //没有对象，返回null
    }

    /**
     *作者：joey
     *描述：通过输入的url参数，并附加上用户名与密码，进行url访问。将访问得到的输出流进行返回。针对单个流，进行长时间的读取
     */
    public String getOneInputStreamFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        // Popup Window to request username/password password
        MyAuthenticator ma = new MyAuthenticator();
        String userPassword = ma.getPasswordAuthentication();
        // Encode String
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        URLConnection uc = url.openConnection();
        uc.setConnectTimeout(1000);
        uc.setReadTimeout(1000);
        uc.setRequestProperty("Authorization", "Basic " + encoding);
        String myCookie = "ptz_ctl_id=225112";
        uc.setRequestProperty("Cookie", myCookie);
        InputStream content = (InputStream) uc.getInputStream();                //触发访问url的操作
        if (content != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(content));
                StringBuilder tStringBuffer = new StringBuilder();
                String sTempOneLine = "";
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();                                //返回String
            } catch (Exception ex) {
                logger.info(ex);
                return ex.toString();                                           //返回异常信息
            }
        }
        return "null";                                                          //没有对象，返回null
    }
}
