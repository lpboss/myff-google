package ff.xsocket;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

public class FlexServerHandler implements IDataHandler, IConnectHandler,
		IIdleTimeoutHandler, IConnectionTimeoutHandler, IDisconnectHandler {

	/**
	 * 即当建立完连接之后可以进行的一些相关操作处理。包括修改连接属性、准备资源、等！ 连接的成功时的操作
	 */
	@Override
	public boolean onConnect(INonBlockingConnection connection)
			throws IOException, BufferUnderflowException,
			MaxReadSizeExceededException {

		String ip = connection.getRemoteAddress().getHostAddress();

		System.out.println("Flex客户端(" + ip + ":" + connection.getLocalPort()
				+ ")已连接！");

		return true;
	}

	/**
	 * 即如果失去连接应当如何处理？ 需要实现 IDisconnectHandler 这个接口 连接断开时的操作
	 */
	@Override
	public boolean onDisconnect(INonBlockingConnection connection)
			throws IOException {
		if (connection != null && connection.isOpen()) {
			String ip = connection.getRemoteAddress().getHostAddress();
			System.out.println("Flex客户端(" + ip + ":"
					+ connection.getLocalPort() + ")已断开！");
		}
		return false;
	}

	/**
	 * 即这个方法不光是说当接收到一个新的网络包的时候会调用而且如果有新的缓存存在的时候也会被调用。而且 当连接被关闭的时候也会被调用的!
	 */
	@Override
	public boolean onData(INonBlockingConnection connection)
			throws IOException, BufferUnderflowException,
			ClosedChannelException, MaxReadSizeExceededException {
		String data = connection.readStringByDelimiter("\n");
		if (data != null && data.indexOf("<headIp>") > -1
				&& data.indexOf("</headIp>") > -1) {

			String headIp = data.substring(8, data.indexOf("</headIp>"));
			ServerUtil.getInstance().sendHeadInfo(connection, headIp);

			while (connection != null && connection.isOpen()) {

				ServerUtil.getInstance().sendHeadInfo(connection, headIp);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		return true;
	}

	/**
	 * 请求处理超时的处理事件
	 */
	@Override
	public boolean onIdleTimeout(INonBlockingConnection connection)
			throws IOException {
		if (connection != null && connection.isOpen()) {
			String ip = connection.getRemoteAddress().getHostAddress();
			System.out.println("Flex客户端(" + ip + ":"
					+ connection.getLocalPort() + ")请求处理超时！");
		}

		return false;
	}

	/**
	 * 连接超时处理事件
	 */
	@Override
	public boolean onConnectionTimeout(INonBlockingConnection connection)
			throws IOException {
		if (connection != null && connection.isOpen()) {
			String ip = connection.getRemoteAddress().getHostAddress();
			System.out.println("Flex客户端(" + ip + ":"
					+ connection.getLocalPort() + ")连接超时！");
		}

		return false;
	}

}
