package com.crystal.framework.Frameworkpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DeploymentHelper {

	public static ChannelSftp setupJsch(List<File> lst,String privateKey,String username,String hostname,int port,String warNameOnServer) throws JSchException, SftpException, IOException {
		JSch jsch = new JSch();
		jsch.addIdentity(privateKey);
		Session session = jsch.getSession(username, hostname, port);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();

		boolean needsRestart = false;

		Channel channel = session.openChannel("sftp");
		channel.setInputStream(System.in);
		channel.setOutputStream(System.out);
		channel.connect();
		ChannelSftp c = (ChannelSftp) channel;

		for (File f : lst) {
			if (f.getName().contains(".class")) {
				String pathStructure = StringUtils.substringBetween(f.getAbsolutePath(), "\\build", f.getName());
				pathStructure = pathStructure.replace("\\", "/");
				c.put(f.getAbsolutePath(), "/home/ubuntu/apache-tomcat-8.5.55/webapps/" + warNameOnServer
						+ "/WEB-INF/" + pathStructure + f.getName());
				System.out.println("files copied from " + f.getAbsolutePath());
				System.out.println("files copied to " + "/home/ubuntu/apache-tomcat-8.5.55/webapps/"
						+ warNameOnServer + "/WEB-INF/" + pathStructure + f.getName());
				needsRestart = true;
			}

			if (f.getName().contains(".jsp") || f.getName().contains(".js") || f.getName().contains(".css")) {
				String pathStructure = StringUtils.substringBetween(f.getAbsolutePath(), "\\WebContent", f.getName());
				pathStructure = pathStructure.replace("\\", "/");
				c.put(f.getAbsolutePath(), "/home/ubuntu/apache-tomcat-8.5.55/webapps/" + warNameOnServer
						+ pathStructure + f.getName());
				System.out.println("files copied from " + f.getAbsolutePath());
				System.out.println("files copied to " + "/home/ubuntu/apache-tomcat-8.5.55/webapps/"
						+ warNameOnServer + pathStructure + f.getName());

			}

		}

		System.out.println("Need to restart " + needsRestart);
		if (needsRestart) {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("sudo bash \"/home/ubuntu/apache-tomcat-8.5.55/bin/shutdown.sh\"");
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();
			StringBuilder outputBuffer = new StringBuilder();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}
			System.out.println(outputBuffer.toString());

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("sudo bash \"/home/ubuntu/apache-tomcat-8.5.55/bin/startup.sh\"");
			commandOutput = channel.getInputStream();
			channel.connect();
			readByte = commandOutput.read();
			outputBuffer = new StringBuilder();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}
			System.out.println(outputBuffer.toString());
			channel.disconnect();
		}

		c.exit();
		System.out.println("done");
		return c;
	}
	
	public static List<String> getListOfFiles(InputStream in) throws FileNotFoundException
	{
		String result = new BufferedReader(new InputStreamReader(in))
				   .lines().collect(Collectors.joining("\n"));
		List<String> lst=Arrays.asList(result.split("\n"));
		return lst;
	}

}
