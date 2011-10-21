package ff.util;  

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 * RTSP流媒体自动录像定时器
 *   
 * @author   Jiangshilin
 * @Date     2011-10-21
 */
public class RTSPStreamRecordTask {	
	private MediaPlayerFactory mediaPlayerFactory;
	private static Map<String, HeadlessMediaPlayer> players;
	private int port=554;//RTSP转发默认端口号
	private String recordFilePath="D:\\RecordFile";//录像默认存储目录
	
	public RTSPStreamRecordTask(){
		mediaPlayerFactory = new MediaPlayerFactory();
		players = new HashMap<String, HeadlessMediaPlayer>();
	}

	/**
	 * 每10分钟/600秒生成一个视频录像文件
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(fixedDelay = 600000)
    public synchronized void record() throws InterruptedException {
		//流媒体参数
		String[] mediaOptions = { ":rtsp-caching=0", ":no-sout-rtp-sap",
				":no-sout-standard-sap", ":sout-all", ":sout-keep" };
		
		//云台ip列表，从数据库获取
		String[] ptzs={"192.168.254.64"};
		for(String ip:ptzs){
			//存储第一通道
			HeadlessMediaPlayer player_file1=players.get(ip+".ch1");
			if(player_file1!=null){
				player_file1.stop();//先停止之前的录像进程
			}else{
				player_file1 = mediaPlayerFactory.newHeadlessMediaPlayer();
				players.put(ip+".ch1", player_file1);					
			}
			
			String input_ch1_file = "rtsp://localhost:"+port+"/"+ip+"/ch1";
			String output_ch1_file = ":sout=#std{access=file,mux=ts,dst="+createFilePath(ip,"ch1")+"\\"+formatFileName()+"}";	
			
			player_file1.setStandardMediaOptions(mediaOptions);
			//开始写入录像文件，如果rtsp转发尚未完成启动，则等待1秒后重试
			while(!player_file1.startMedia(input_ch1_file, output_ch1_file)){
				Thread.sleep(1000);
				player_file1.stop();
				output_ch1_file = ":sout=#std{access=file,mux=ts,dst="+createFilePath(ip,"ch1")+"\\"+formatFileName()+"}";
			}
			System.out.println("Streaming '" + input_ch1_file + "' to '" + output_ch1_file + "'");
			
			//存储第二通道
			HeadlessMediaPlayer player_file2=players.get(ip+".ch2");
			if(player_file2!=null){
				player_file2.stop();//先停止之前的录像进程
			}else{
				player_file2 = mediaPlayerFactory.newHeadlessMediaPlayer();
				players.put(ip+".ch2", player_file2);					
			}
			String input_ch2_file = "rtsp://localhost:"+port+"/"+ip+"/ch2";
			String output_ch2_file = ":sout=#std{access=file,mux=ts,dst="+createFilePath(ip,"ch2")+"\\"+formatFileName()+"}";	
			
			player_file2.setStandardMediaOptions(mediaOptions);
			//开始写入录像文件，如果rtsp转发尚未完成启动，则等待1秒后重试
			while(!player_file2.startMedia(input_ch2_file, output_ch2_file)){
				Thread.sleep(1000);
				player_file2.stop();
				output_ch2_file = ":sout=#std{access=file,mux=ts,dst="+createFilePath(ip,"ch2")+"\\"+formatFileName()+"}";
			}
			
			System.out.println("Streaming '" + input_ch2_file + "' to '" + output_ch2_file + "'");
		}
	}
	
	
	/**
	 * 定时检查录像存储目录所在磁盘的剩余空间，空间不足时删除时间最早的一批录像文件，以释放磁盘空间
	 * 每小时执行一次
	 * @throws
	 */
	@Scheduled(fixedDelay = 3600000)
    public synchronized void freeDiskSpace() {
		//检查可用磁盘空间
		File file =new File(recordFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		float stat_g=(float)file.getFreeSpace()/1024/1024/1024;
		
		//当可用磁盘空间不足10G时，执行删除操作
		if(stat_g<10){
			//获取所有录像文件的列表
			ArrayList<File> fileList=listFiles(recordFilePath);			
			File[] files=fileList.toArray(new File[0]);
			
			//根据最后修改时间对录像文件进行排序
			Arrays.sort(files, new CompratorByLastModified());
			
			//删除10%的录像文件，即大约释放10%的磁盘空间
			for(int i=0;i<=files.length/10;i++){
				File f=files[i];
				if(f!=null){
					f.delete();
				}
			}			
		}	
	}
	
	/**
	 * 列出指定目录及其子目录下所有的文件
	 * @param directory 指定目录绝对路径
	 * @return
	 * @throws
	 */
	private ArrayList<File> listFiles(String directory){
		ArrayList<File> fileList=new ArrayList<File>();
		
		File file=new File(directory);
	    File[] fileList1=file.listFiles();
	    for(File f:fileList1){
	    	if(f.isDirectory()){
	    		ArrayList<File> fileList_sub=listFiles(f.getAbsolutePath());
	    		fileList.addAll(fileList_sub);
	    	}else{
		    	fileList.add(f);
	    	}
	    }

	    return fileList;
	}
	
	/**
	 * 根据当前时间自动创建录像存放目录并返回
	 * @param ip 云台或摄像机ip
	 * @param ch 摄像机通道编号
	 * @return 录像存放目录
	 * @throws
	 */
	private String createFilePath(String ip,String ch){
		Date now=new Date();
		String now_date=new SimpleDateFormat("yyyyMMdd").format(now);
		String path=recordFilePath+"\\"+now_date+"\\"+ip+"."+ch;
		File file =new File(path);
		if(!file.exists()){
			file.mkdirs();
		}

		return path;
	}
	
	/**
	 * 根据当前时间自动生成录像存放文件名
	 * @return
	 * @throws
	 */
	private String formatFileName(){
		Date now=new Date();
		String now_date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(now);
		String fileName=now_date+".mp4";

		return fileName;
	}
	
	/**
	 * 用于文件最后修改时间的比较器
	 */
	class CompratorByLastModified implements Comparator {
		public int compare(Object o1, Object o2) {
			File file1 = (File) o1;
			File file2 = (File) o2;
			long diff = file1.lastModified() - file2.lastModified();
			if (diff > 0)
				return 1;
			else if (diff == 0)
				return 0;
			else
				return -1;
		}

		public boolean equals(Object obj) {
			return true;
		}
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRecordFilePath() {
		return recordFilePath;
	}

	public void setRecordFilePath(String recordFilePath) {
		this.recordFilePath = recordFilePath;
	}
	
}
  
