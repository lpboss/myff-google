package ff.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class FtpClientUtil{
    
    //连接ftp
    public String connectFtp(String server,String username,String password,String dir){
        FTPClient ftp = new FTPClient();
        String info = " ";
        try {
            int reply;
            ftp.connect(server);
            ftp.login(username, password);
            
            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                info = "login failure ";
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            
            //指明ftp目录
            List<String> list = new ArrayList<String>();
            List<String> filesList = getFilesList(list,ftp,dir);
            Iterator<String> it = list.iterator();
            while(it.hasNext()){
                info += it.next()+",";
            }
        } catch (SocketException ex) {
            Logger.getLogger(FtpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FtpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return getInfo(info);
    }
    
    public String getInfo(String info){
        String str;
        if("".equals(info)){
            return "{totalProperty:0,root:[]}";
        }else{
            String[] array = info.split(",");
            str = "{totalProperty:"+array.length+",root:[";
            for(String i : info.split(",")){
                str += "{title:\"" + i + "\"},";
            }
            str = str.substring(0,str.length()-1) + "]}";
            return str;
        }
    }
    
    public List<String> getFilesList(List list,FTPClient ftp,String remote) throws IOException{
        FTPFile[] files = ftp.listFiles(remote);
        
        for (FTPFile f : files) {                
            if(f.getType()==1){
                getFilesList(list,ftp,remote+f.getName()+"/");
            } else {
                list.add("ftp://root:pass@"+ftp.getRemoteAddress().getHostName()+remote+f.getName());
            }
        }
        return list;
    }
    
    
}