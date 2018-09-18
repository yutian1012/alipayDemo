/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * HttpUtil - Misc HTTP Utility Functions
 */
public class UtilHttp {

    /** URL Encodes a Map of arguements */
    public static String urlEncodeArgs(Map<String, ? extends Object> args) {
        return urlEncodeArgs(args, true);
    }

    /** URL Encodes a Map of arguements */
    public static String urlEncodeArgs(Map<String, ? extends Object> args, boolean useExpandedEntites) {
        StringBuilder buf = new StringBuilder();
        if (args != null) {
            for (Map.Entry<String, ? extends Object> entry: args.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                String valueStr = null;
                if (name != null && value != null) {
                    if (value instanceof String) {
                        valueStr = (String) value;
                    } else {
                        valueStr = value.toString();
                    }
                    if (null!=valueStr) {
                        if (buf.length() > 0) {
                            if (useExpandedEntites) {
                                buf.append("&amp;");
                            } else {
                                buf.append("&");
                            }
                        }
                        try {
							buf.append(URLEncoder.encode(name,"UTF-8")).append('=').append(URLEncoder.encode(valueStr,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
                    }
                }
            }
        }
        return buf.toString();
    }

    
}
