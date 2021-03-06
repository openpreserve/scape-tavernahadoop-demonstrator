/*
 * Copyright 2012 The SCAPE Project Consortium.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package eu.scape_project.tb.rest;

import eu.scape_project.tb.rest.ssl.DefaultConnectionManager;
import eu.scape_project.tb.rest.xml.XPathEvaluator;
import eu.scape_project.tb.rest.xml.XmlResponseParser;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Main class of the simple http rest client. This client provides a simple http
 * rest client with a small set of REST request methods defined in the
 * DefaultHttpRestClient class. It also includes a basic xml parser for
 * processing an XML response.
 *
 * @author Sven Schlarb https://github.com/shsdev
 * @version 1.0
 */
public class DefaultHttpRestClientDemo {
    
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpRestClientDemo.class.getName());

    /**
     * Main method of the simple http rest client.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, DefaultHttpClientException {

        // The examples require a running instance of the Taverna Server 2.4.1
        // http://dev.mygrid.org.uk/wiki/display/taverna/A+Beginner%27s+Installation+Guide+to+Taverna+Server
        // SECURE/INSECURE mode is configured in
        // apache-tomcat-${tomcat-version}/webapps/TavernaServer.${taverna-version}/WEB-INF/web.xml
        // Uncomment one of the context parameters (default is secure mode) to 
        // activate the secure or the insecure profile:
        // <param-value>WEB-INF/secure.xml</param-value>
        // <!--param-value>WEB-INF/insecure.xml</param-value-->

        // INSECURE
        // Example: Taverna Server REST API (list runs)
        // http://localhost:8080/TavernaServer.2.4.1/rest/runs
//        DefaultHttpAuthRestClient insecureRestClient = new DefaultHttpAuthRestClient("http", "localhost", 8080, "/TavernaServer.2.4.1/rest");
//        insecureRestClient.setUser("taverna");
//        insecureRestClient.setPassword("taverna");
//        insecureRestClient.executeGet("/runs", "application/xml");

        // SECURE
        // Example: Taverna Server REST API (list runs)
        // https://localhost:8443/TavernaServer.2.4.1/rest/runs
//        DefaultHttpsAuthRestClient secureRestClient = new DefaultHttpsAuthRestClient(DefaultConnectionManager.getInstance(), "https", "localhost", 8443, "/TavernaServer.2.4.1/rest");
//        secureRestClient.setUser("taverna");
//        secureRestClient.setPassword("taverna");
//        secureRestClient.executeGet("/runs", "application/xml");
        
        
    }
}
