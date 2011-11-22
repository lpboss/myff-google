package ff.server;

import java.util.HashMap;
import java.util.Map;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 * RTSP视频流转发服务器，使用了基于vlc的vlcj开源组件，需要在服务器端安装vlc播放器
 * 
 * @author Jiangshilin
 * @Date 2011-10-17
 */
public class RTSPStreamServerHandler implements Runnable{
	private MediaPlayerFactory mediaPlayerFactory;
	private static Map<String, HeadlessMediaPlayer> players = new HashMap<String, HeadlessMediaPlayer>();
	private int port=554;//RTSP转发默认端口号
	private String jnaLibPath="C:\\Program Files\\VideoLAN\\VLC";//libvlc.dll默认目录，即vlc的安装目录
	
	@Override
	public void run() {
		System.setProperty("jna.library.path", jnaLibPath);		
		streamingToRTSP();
			
	}
	
	private void streamingToRTSP(){
		//流媒体参数
		String[] mediaOptions = { ":rtsp-caching=50", ":no-sout-rtp-sap",
				":no-sout-standard-sap", ":sout-all", ":sout-keep" };
		
		mediaPlayerFactory = new MediaPlayerFactory();

		//云台ip列表，从数据库获取
		String[] ptzs={"192.168.254.64"};
		for(String ip:ptzs){	
			//转发第一通道
			String input_ch1 = "rtsp://admin:12345@"+ip+"/h264/ch1/main/av_stream";
			String output_ch1 = ":sout=#rtp{sdp=rtsp://:"+port+"/"+ip+"/ch1}";	
			
			HeadlessMediaPlayer player_ch1 = mediaPlayerFactory.newHeadlessMediaPlayer();
			player_ch1.setStandardMediaOptions(mediaOptions);
			player_ch1.playMedia(input_ch1, output_ch1);
			
			players.put(ip+".ch1", player_ch1);	
			System.out.println("Streaming '" + input_ch1 + "' to '" + output_ch1 + "'");
			
			//转发第二通道
			String input_ch2 = "rtsp://admin:12345@"+ip+"/h264/ch2/main/av_stream";
			String output_ch2 = ":sout=#rtp{sdp=rtsp://:"+port+"/"+ip+"/ch2}";				
			
			HeadlessMediaPlayer player_ch2 = mediaPlayerFactory.newHeadlessMediaPlayer();
			player_ch2.setStandardMediaOptions(mediaOptions);
			player_ch2.playMedia(input_ch2, output_ch2);
			
			players.put(ip+".ch2", player_ch2);	
			System.out.println("Streaming '" + input_ch2 + "' to '" + output_ch2 + "'");
			
		}
	}
	
	private void streamingToHTTP(){
		//流媒体参数
		String[] mediaOptions = { ":sout-udp-caching=0", ":no-sout-rtp-sap",
				":no-sout-standard-sap", ":sout-all", ":sout-keep" };
		
		mediaPlayerFactory = new MediaPlayerFactory();

		//云台ip列表，从数据库获取
		String[] ptzs={"192.168.254.64"};
		for(String ip:ptzs){	
			//转发第一通道
			String input_ch1 = "rtsp://admin:12345@"+ip+"/h264/ch1/main/av_stream";
			String output_ch1 = ":sout=#transcode{vcodec=h264,acodec=mp3,samplerate=44100}:duplicate{dst=std{access=http{mime=video/x-flv},mux=ffmpeg{mux=flv},dst=:33333/stream.flv}}";	
			
			HeadlessMediaPlayer player_ch1 = mediaPlayerFactory.newHeadlessMediaPlayer();
			player_ch1.setStandardMediaOptions(mediaOptions);
			player_ch1.playMedia(input_ch1, output_ch1);
			
			players.put(ip+".ch1", player_ch1);	
			System.out.println("Streaming '" + input_ch1 + "' to '" + output_ch1 + "'");
			
			//转发第二通道
			String input_ch2 = "rtsp://admin:12345@"+ip+"/h264/ch2/main/av_stream";
			String output_ch2 = ":sout=#rtp{sdp=rtsp://:"+port+"/"+ip+"/ch2}";				
			
			HeadlessMediaPlayer player_ch2 = mediaPlayerFactory.newHeadlessMediaPlayer();
			player_ch2.setStandardMediaOptions(mediaOptions);
			player_ch2.playMedia(input_ch2, output_ch2);
			
			players.put(ip+".ch2", player_ch2);	
			System.out.println("Streaming '" + input_ch2 + "' to '" + output_ch2 + "'");
			
		}
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getJnaLibPath() {
		return jnaLibPath;
	}
	public void setJnaLibPath(String jnaLibPath) {
		this.jnaLibPath = jnaLibPath;
	}

	public static void main(String[] args){
		//RTSPStreamServerHandler rtspStreamServerHandler=new RTSPStreamServerHandler();
		//Thread rtspStreamServer = new Thread(rtspStreamServerHandler);
		//rtspStreamServer.start();
		//DecimalFormat df = new DecimalFormat("0.00");
		//System.out.println(df.format(333.8));
	}

}
