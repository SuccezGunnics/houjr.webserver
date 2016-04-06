package com.succez.server.app;

import com.succez.server.app.utils.SeparatorUtils;

public class HandlerConst {

	public static final Integer BROWSABLE_DIRECTORY = 0;
	public static final Integer BROWSABLE_TEXT = 1;
	public static final Integer BROWSABLE_IMAGE = 2;
	public static final Integer DOWNLOAD_FILE = 3;
	public static final Integer ILLEGAL_PATH = -1;
	
	public static final String LINE_SEPARATOR = SeparatorUtils.getLineSeparator();
	public static final String HEAD_TEXT = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:text/html"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR+LINE_SEPARATOR;
	public static final String HEAD_IMAGE = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:image/jpeg"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR+LINE_SEPARATOR;
	public static final String HEAD_DOWNLOAD = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:application/octet-stream"+LINE_SEPARATOR+"Accept-Ranges: bytes"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR;
}