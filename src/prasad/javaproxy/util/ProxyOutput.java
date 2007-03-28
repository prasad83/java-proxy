/**
 * @(#)ProxyOutput.java
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

/**
 * ProxyOutput class stores the result of Proxy Parser.
 * @author prasad
 *
 */
public class ProxyOutput {
	public static final String BLANK = "";
	public static final String HTML = "html";
	public static final String PLAIN = "plain";
	
	private String _type;
	private String _output;
	
	public ProxyOutput(String type, String output) {
		this._type = type;
		this._output = output;
	}
	public String getType() {
		return _type;
	}
	public String getOutput() {
		return _output;
	}
	public String getContentType() {
		if(HTML.equalsIgnoreCase(getType())) return "text/html";
		if(PLAIN.equalsIgnoreCase(getType())) return "text/plain";
		else return "";
	}
	public boolean isBlankContentType() {
		return BLANK.equals(getContentType());
	}
}
