package com.succez.server.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.succez.server.utils.CommonUtils;
import com.succez.server.utils.XmlUtils;


public class HandlerConst {

	public static final String LINE_SEPARATOR = CommonUtils.getLineSeparator();
	public static final String HEAD_TEXT = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:text/html"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR+LINE_SEPARATOR;
	public static final String HEAD_IMAGE = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:image/jpeg"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR+LINE_SEPARATOR;
	public static final String HEAD_DOWNLOAD = "HTTP/1.0200OK"+LINE_SEPARATOR+"Content-Type:application/octet-stream"+LINE_SEPARATOR+"Accept-Ranges: bytes"+LINE_SEPARATOR+"Server:myserver"+LINE_SEPARATOR;
	
	public static final String HTML_PRE = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>PageList</title></head><body><table><tr><th>pageList:</th></tr>";
	public static final String HTML_LAST = "</table></body></html>";
	
	public static final String DEFAULT_CHARSET;
	public static final String WEB_ROOT;
	
	public static final List<String> textList;
	public static final List<String> imageList;
	
	static {
		File file = new File("src/com/succez/server/app/web.xml");
		Element root = XmlUtils.getRoot(XmlUtils.load(file));
		Element defaultCharsetElem = XmlUtils.getChildByName(root,
				"default-charset");
		DEFAULT_CHARSET = XmlUtils.getElementText(defaultCharsetElem);
		Element defaultWebRootElems = XmlUtils.getChildByName(root,
				"default-root");
		WEB_ROOT = XmlUtils.getElementText(defaultWebRootElems);
		
		Element brosableElems = XmlUtils.getChildByName(root, "params-browsable");
		Element[] brosableTextElems = XmlUtils.getChildrenByAttrubte(brosableElems, "type", "text");
		Element[] brosableImageElems = XmlUtils.getChildrenByAttrubte(brosableElems, "type", "image");
		
		textList = new ArrayList<String>(brosableTextElems.length);
		imageList = new ArrayList<String>(brosableImageElems.length);
		
		for(Element e : brosableTextElems){
			textList.add(XmlUtils.getElementText(e));
		}
		for(Element e : brosableImageElems){
			imageList.add(XmlUtils.getElementText(e));
		}
	}
	
}