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

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple HTTP Rest client with BASIC authentication. If the request requires
 * BASIC authentication, using this client a username and password can be
 * defined (DefaultHttpAuthRestClient.setCredentials).
 *
 * @author Sven Schlarb https://github.com/shsdev
 * @version 1.0
 */
public class DefaultHttpAuthRestClient extends DefaultHttpRestClient {

    protected String user;
    protected String password;
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpAuthRestClient.class.getName());

    /**
     * Constructor of the simple authentication http rest client (insecure).
     *
     * @param host Host
     * @param port Port
     * @param basePath Base path
     */
    public DefaultHttpAuthRestClient(String scheme, String host, int port, String basePath) {
        super(scheme, host, port, basePath);
    }

    /**
     * Constructor of the simple authentication http rest client (secure).
     *
     * @param host Host
     * @param port Port
     * @param basePath Base path
     */
    protected DefaultHttpAuthRestClient(BasicClientConnectionManager bccm) {
        super(bccm);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        logger.debug("Setting password ...");
        this.password = password;
        if (user != null) {
            setCredentials();
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        logger.debug("Setting user ...");
        this.user = user;
        if (password != null) {
            setCredentials();
        }
    }

    /**
     * Define username and password for BASIC authentication.
     *
     * @param user User name
     * @param password Password
     */
    protected void setCredentials() {
        BasicCredentialsProvider bcp = new BasicCredentialsProvider();
        bcp.setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(this.user, this.password));
        this.setCredentialsProvider(bcp);
        // Auth context
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new org.apache.http.auth.AuthScope(
                org.apache.http.auth.AuthScope.ANY_HOST,
                org.apache.http.auth.AuthScope.ANY_PORT,
                org.apache.http.auth.AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(this.user, this.password));
        httpContext.setAttribute(ClientContext.CREDS_PROVIDER, credsProvider);
    }
}
