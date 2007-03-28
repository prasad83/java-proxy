/**
 * @(#)JProxyServlet.java
 * Copyright (c) 2007 Prasad.A
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package prasad.javaproxy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * ProxyUtil Class enables web page proxy conversion. The links on html page from the web
 * are converted to be proxied. The <link>, <script> and few others tags are not changed.
 * To handle proper working of the page <base> tag is added (guessing href from incoming url)
 * if not present.
 * 
 * @author prasad
 *
 */
public class ProxyUtil {
	/**
	 * Proxy the page with url changed if required.
	 * @param inurl Input Url to get the content from.
	 * @param linkPrefix Proxy Url to be used for the links.
	 * @param linkParam Proxy Url Parameter name for passing the original link value.
	 * @return ProxyOutput
	 * @throws ParserException
	 */
	public static ProxyOutput doProxify(String inurl, String linkPrefix, String linkParam) throws ParserException {
		PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
		factory.registerTag(new CustomLinkTag(linkPrefix, linkParam));

		Parser parser = new Parser(inurl);
		parser.setNodeFactory(factory);
		NodeList nodeList = parser.parse(null);
		
		NodeList htmlNodeList = nodeList.extractAllNodesThatMatch(new TagNameFilter("HTML"), true);
		if(htmlNodeList.size() == 0) {
			return (new ProxyOutput(ProxyOutput.PLAIN, nodeList.toHtml(true)));
		}
		
		NodeList heads = htmlNodeList.extractAllNodesThatMatch(new TagNameFilter("HEAD"), true);
		
		
		/**
		 * Let us add <base> tag if not already present, this will help the relative paths to work fine
		 * even though the page is being proxied by us. 
		 */
		if(heads.size() > 0) { // HEAD Tag is optional
			Node head = heads.elementAt(0); // there should be only one
			NodeList headNodeList = head.getChildren();
			NodeList baseNodeList = headNodeList.extractAllNodesThatMatch(new TagNameFilter("BASE"));
			if(baseNodeList.size() == 0) {
				String baseurl = ProxyUtilHelper.getBaseUrl(inurl);
				BaseHrefTag baseHrefTag = new BaseHrefTag();
				baseHrefTag.setBaseUrl(baseurl);
				headNodeList.prepend(baseHrefTag);
			}
		}
		return (new ProxyOutput(ProxyOutput.HTML, nodeList.toHtml()));
	}
}

/**
 * Helper class to enable web page proxy.
 */
class ProxyUtilHelper {
	static String getProtocol(String url) {
		int colonIndex = url.indexOf(":");
		if(colonIndex != -1) return url.substring(0, colonIndex);
		return "";
	}
	
	static String getBaseUrl(String inurl) {
		String protocol = getProtocol(inurl);		
		if(protocol.endsWith("://")) protocol += "://";
		
		String baseurl = "";
		
		int qmarkPos = inurl.indexOf("?");
		if(qmarkPos == -1) { qmarkPos = inurl.length(); }
		inurl = inurl.substring(0, qmarkPos);
		
		// Search for protocol://server.../ first
		if(inurl.toLowerCase().indexOf(protocol) != -1) {
			baseurl = inurl.substring(0, protocol.length());
			inurl = inurl.substring(protocol.length());
		}
		
		// Search if the url has more slash take things only till last slash
		int lastSlashIndex = inurl.lastIndexOf("/");
		if(lastSlashIndex == -1) lastSlashIndex = inurl.length();
		baseurl += inurl.substring(0, lastSlashIndex);
		if(!baseurl.endsWith("/")) baseurl += "/";
		return baseurl;
	}
}

/**
 * Customized version of link tag handling.
 */
class CustomLinkTag extends LinkTag {
	private static final long serialVersionUID = 1L;
	private String linkPrefix = "";
	private String linkParamName = "";
	public CustomLinkTag(String linkPrefix, String linkParamName) {
		this.linkPrefix = (linkPrefix == null)? "" : linkPrefix;
		this.linkParamName = (linkParamName == null)? "url" : linkParamName;
		
		if(this.linkPrefix.indexOf("?") == -1) this.linkPrefix += "?";
	}
	public void doSemanticAction() throws ParserException {
		try {
			String newLink = linkPrefix + "&" + linkParamName + "=" + URLEncoder.encode(getLink(), "utf-8");
			setLink(newLink);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
