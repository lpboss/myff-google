/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

/**
 *
 * @author jerry
 */
public class FTPClientTest {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FtpClientUtil ftpUtil = new FtpClientUtil();
        //ftpUtil.connectFtp("ftp.fu-berlin.de", "anonymous", "anonymous");
        ftpUtil.connectFtp("hanbaopower.greenserver.cn", "admin_hanbaopower", "VnHDYvit","/hanbaopower.greenserver.cn/tmp/");
    }
}
