package ff.util;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

import ff.server.FlexAuthServerHandler;
import ff.server.FlexServerHandler;
import ff.server.PTZAlertServerHandler;
import ff.server.PTZControlServerHandler;
import ff.server.RTSPStreamServerHandler;

/**
 * 本项目附属服务器启动类，包括串口通信服务器和流媒体转发服务器，以Spring Bean的方式随容器启动而启动
 * 
 * @author Jiangshilin
 * @Date 2011-10-18
 */
public class ServerBootUtil{
	static Logger logger = Logger.getLogger(ServerBootUtil.class.getName());
	
	private IServer ptzControlServer;
	private IServer ptzAlertServer;
	private IServer flexServer;
	private IServer flexAuthServer;
	private Thread rtspStreamServer;
	
	@Autowired
	private PTZControlServerHandler ptzControlServerHandler;
	@Autowired
	private PTZAlertServerHandler ptzAlertServerHandler;
	@Autowired
	private FlexServerHandler flexServerHandler;
	@Autowired
	private FlexAuthServerHandler flexAuthServerHandler;
	@Autowired
	private RTSPStreamServerHandler rtspStreamServerHandler;
	
	private int ptzControlServerPort;
	private int ptzAlertServerPort;	
	private int flexServerPort; 
	private int flexAuthServerPort;
	private int rtspStreamServerPort;

	@PostConstruct
	public void start() throws IOException{
	
		// 启动云台控制服务
		ptzControlServer = new Server(ptzControlServerPort, ptzControlServerHandler);
		ptzControlServer.start();

		// 启动云台报警服务
		ptzAlertServer = new Server(ptzAlertServerPort, ptzAlertServerHandler);
		ptzAlertServer.start();

		// 启动flex安全沙箱验证服务
		flexAuthServer = new Server(flexAuthServerPort,
				flexAuthServerHandler);
		flexAuthServer.start();

		// 启动flex服务
		flexServer = new Server(flexServerPort, flexServerHandler);
		flexServer.setIdleTimeoutMillis(60000);
		flexServer.setConnectionTimeoutMillis(3600000);
		flexServer.start();

		// 启动流媒体转发服务
		rtspStreamServerHandler.setPort(rtspStreamServerPort);
		rtspStreamServer = new Thread(rtspStreamServerHandler);
		rtspStreamServer.start();
	}

	public int getPtzControlServerPort() {
		return ptzControlServerPort;
	}

	public void setPtzControlServerPort(int ptzControlServerPort) {
		this.ptzControlServerPort = ptzControlServerPort;
	}

	public int getPtzAlertServerPort() {
		return ptzAlertServerPort;
	}

	public void setPtzAlertServerPort(int ptzAlertServerPort) {
		this.ptzAlertServerPort = ptzAlertServerPort;
	}

	public int getFlexServerPort() {
		return flexServerPort;
	}

	public void setFlexServerPort(int flexServerPort) {
		this.flexServerPort = flexServerPort;
	}

	public int getFlexAuthServerPort() {
		return flexAuthServerPort;
	}

	public void setFlexAuthServerPort(int flexAuthServerPort) {
		this.flexAuthServerPort = flexAuthServerPort;
	}

	public int getRtspStreamServerPort() {
		return rtspStreamServerPort;
	}

	public void setRtspStreamServerPort(int rtspStreamServerPort) {
		this.rtspStreamServerPort = rtspStreamServerPort;
	}
	
	
}
