package com.lb.backend.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FileUtils {

	public static String encodeDownloadFilename(String filename, String agent) throws IOException { // 如果是火狐浏览器
		if (agent.contains("Firefox")) {
			filename = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(filename.getBytes(StandardCharsets.UTF_8)) + "?=";
			filename = filename.replaceAll("\r\n", "");
			// IE及其他浏览器
		} else {
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		}
		return filename;
	}
}
