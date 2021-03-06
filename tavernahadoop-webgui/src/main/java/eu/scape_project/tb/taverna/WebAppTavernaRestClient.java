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
package eu.scape_project.tb.taverna;

import eu.scape_project.tb.config.TavernaConfig;
import eu.scape_project.tb.model.entity.Workflow;
import eu.scape_project.tb.model.entity.WorkflowRun;
import eu.scape_project.tb.taverna.rest.TavernaClientException;
import eu.scape_project.tb.taverna.rest.TavernaServerRestClient;
import eu.scape_project.tb.taverna.rest.TavernaWorkflowStatus;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application taverna rest client. Singleton class for the
 * tavernahttp-restclient module which performs requests to the Taverna Server.
 *
 * @author Sven Schlarb https://github.com/shsdev
 * @version 0.1
 */
public class WebAppTavernaRestClient implements Serializable {

    private Logger logger = LoggerFactory.getLogger(WebAppTavernaRestClient.class.getName());
    private TavernaConfig config;
    private String scheme;
    private String host;
    private int port;
    private int httpsReplacePort;
    private final String basepath;
    private final TavernaServerRestClient tavernaRestClient;
    // Singleton Instance
    private static WebAppTavernaRestClient instance = null;

    public static WebAppTavernaRestClient getInstance() {
        if (instance == null) {
            instance = new WebAppTavernaRestClient();
        }
        return instance;
    }

    private WebAppTavernaRestClient() {
        config = new TavernaConfig();
        scheme = config.getProp("taverna.server.scheme");
        host = config.getProp("taverna.server.host");
        try {
            port = Integer.parseInt(config.getProp("taverna.server.port"));
            httpsReplacePort = Integer.parseInt(config.getProp("taverna.server.https.replace.port"));
        } catch (java.lang.NumberFormatException ex) {
            logger.error("Invalid taverna host port number configuration.");
        }
        basepath = config.getProp("taverna.server.restapi.basepath");
        tavernaRestClient = new TavernaServerRestClient(scheme, host, port, basepath);
        tavernaRestClient.setUser(config.getProp("taverna.server.username"));
        tavernaRestClient.setPassword(config.getProp("taverna.server.password"));
        tavernaRestClient.setHttpsReplacePort(httpsReplacePort);
    }

    public TavernaServerRestClient getClient() {
        return tavernaRestClient;
    }

    /**
     * Submit, initialise, and run a taverna workflow using the
     * tavernahttp-restclient module. The workflow run is initialised using the
     * workflow object and the related workflow run object. The key-value map of
     * the workflow input ports is provided additionally because it allows to
     * run the workflow with default values defined by the workflow input ports
     * objects related to the workflow or any transient values that need not to
     * be persisted.
     *
     * @param workflow Workflow object
     * @param workflowRun Workflow run object
     * @param kvMap Key value map of workflow input ports
     * @return Success/failure of the workflow run creation
     */
    public Boolean run(Workflow workflow, WorkflowRun workflowRun, Map<String, String> kvMap) throws TavernaClientException {
        Boolean success = false;
        try {
            // 1. POST WORKFLOW

            File workflowFile = new File(config.getProp("taverna.workflow.upload.path") + workflow.getFilename());
            String uuidRURL = tavernaRestClient.submitWorkflow(workflowFile);
            // Set UUID resource URL
            workflowRun.setUuidBaseResourceUrl(uuidRURL);
            logger.info(workflowRun.getUuidBaseResourceUrl());

            // 2. Inject UUID in key-value map
            kvMap.put("uuid", workflowRun.getUuid());

            // 3. PUT INPUT PORTS
            URL resourceUrl = new URL(workflowRun.getUuidBaseResourceUrl());
            for (String key : kvMap.keySet()) {
                tavernaRestClient.setWorkflowInput(resourceUrl, key, kvMap.get(key));
            }

            // 4. Run workflow
            tavernaRestClient.setWorkflowStatus(resourceUrl, TavernaWorkflowStatus.OPERATING);

            success = true;

        } catch (MalformedURLException ex) {
            logger.error("Error", ex);
        }
        return success;
    }

    public TavernaWorkflowStatus getRunStatus(String uuidBaseResourceUrl) throws TavernaClientException {

        TavernaWorkflowStatus status = TavernaWorkflowStatus.UNDEFINED;
        URL url = null;
        try {
            url = new URL(uuidBaseResourceUrl);
            status = tavernaRestClient.getWorkflowStatus(url);
        } catch (MalformedURLException ex) {
            logger.error("Malformed URL error", ex);
        }
        return status;
    }
}
