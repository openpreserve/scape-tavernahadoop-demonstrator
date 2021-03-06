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
package eu.scape_project.tb.taverna.rest;

/**
 * Content types. This class is a placeholder of the apache class
 * org.apache.http.entity.ContentType which is available in newer versions of
 * the Apache HttpCore API.
 *
 * @author shsdev https://github.com/shsdev
 * @version 1.0
 */
public enum TavernaWorkflowStatus {

    /**
     * Taverna workflow status
     */
    UNDEFINED {
        @Override
        public String toString() {
            return "Undefined";
        }
    },
    /**
     * Taverna workflow status
     */
    SUBMITTED {
        @Override
        public String toString() {
            return "Submitted";
        }
    },
    /**
     * Taverna workflow status
     */
    POLLING {
        @Override
        public String toString() {
            return "Polling ...";
        }
    },
    /**
     * Taverna workflow status
     */
    INITIALISED {
        @Override
        public String toString() {
            return "Initialized";
        }
    },
    OPERATING {

        @Override
        public String toString() {
            return "Operating";
        } 
    },
    FINISHED {

        @Override
        public String toString() {
            return "Finished";
        }
    },
    ERROR {
        @Override
        public String toString() {
            return "Error";
        }
    },
    NONEXISTENT {
        @Override
        public String toString() {
            return "Nonexistent";
        }
    }
}