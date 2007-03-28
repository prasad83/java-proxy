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
package prasad.javaproxy.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prasad.javaproxy.util.ProxyOutput;
import prasad.javaproxy.util.ProxyUtil;

/**
 * Java Proxying Servlet.
 * @author prasad
 *
 */
public class JProxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		processRequest(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		processRequest(request, response);
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		try {
			String inurl = request.getParameter("inurl");
			
			if(inurl == null || inurl == "") {
				response.sendRedirect("");
				return;
			}
			
			ProxyOutput proxyOutput = ProxyUtil.doProxify(inurl, getBaseUrl(request)+"jproxy", "inurl");
			if(!proxyOutput.isBlankContentType()) {
				response.setContentType(proxyOutput.getContentType());
			}
			response.getWriter().print(proxyOutput.getOutput());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static boolean isNullOrBlank(String input) {
		return (input == null || "".equals(input));
	}
	
	static String getBaseUrl(HttpServletRequest request) {
    	String requri = request.getRequestURI();
        String requrl = request.getRequestURL().toString();
        
        int endIndex = requrl.indexOf(requri);
        if (requri.equals("/")) {
        	String httpScheme = request.getScheme() + "://";
        	int lookupStartIndex = requrl.indexOf(httpScheme) + httpScheme.length(); 
            endIndex = requrl.indexOf(requri, lookupStartIndex);
        }
        
        // In case of request forward's, requri and requrl may not match, lets use context path
        if (endIndex == -1 && !isNullOrBlank(request.getContextPath())) {
        	endIndex = requrl.indexOf(request.getContextPath());
        }
        
        if (endIndex == -1) endIndex = requrl.length();
        
        StringBuffer baseUrl = new StringBuffer(requrl.substring(0, endIndex));
        baseUrl.append(request.getContextPath());
        
        if (!baseUrl.toString().endsWith("/")) baseUrl.append("/");

        return baseUrl.toString();
    }
    
}
