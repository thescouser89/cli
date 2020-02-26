/*
 * JBoss, Home of Professional Open Source. Copyright 2017 Red Hat, Inc., and individual
 * contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jboss.pnc.bacon.pig.impl.addons.vertx;

import org.jboss.pnc.bacon.pig.impl.addons.AddOn;
import org.jboss.pnc.bacon.pig.impl.config.Config;
import org.jboss.pnc.bacon.pig.impl.pnc.PncBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Paul Gallagher, pgallagh@redhat.com <br>
 *         Date: 2018-11-20
 *
 *         If you run your builds with 'dependency:tree' then you can use this addon to give you the list of compile
 *         scope dependencies that are not redhat builds
 */
public class NotYetAlignedFromDependencyTree extends AddOn {

    private static final Logger log = LoggerFactory.getLogger(NotYetAlignedFromDependencyTree.class);

    public NotYetAlignedFromDependencyTree(
            Config config,
            Map<String, PncBuild> builds,
            String releasePath,
            String extrasPath) {
        super(config, builds, releasePath, extrasPath);
    }

    @Override
    protected String getName() {
        return "notYetAlignedFromDependencyTree";
    }

    @Override
    public void trigger() {
        String filename = extrasPath + "DependencyTreeMissingAlignment.txt";
        PrintWriter file = null;
        log.info("Running NotYetAlignedFromDependencyTree - report is {}", filename);
        try {
            file = new PrintWriter(filename);
            for (PncBuild build : builds.values()) {
                // Make a unique list so we don't get multiples from
                // sub-module's dependency tree list
                List<String> bcLog = build.getBuildLog().stream().distinct().collect(Collectors.toList());
                file.println("-------- [" + build.getId() + "] " + build.getName() + " --------");
                for (String bcLine : bcLog) {
                    if (bcLine.startsWith("[INFO] +") && (bcLine.endsWith(":runtime") || bcLine.endsWith(":compile"))
                            && !bcLine.contains("redhat-")) {
                        file.println(bcLine);
                    }
                }
                file.println();
            }
        } catch (FileNotFoundException e) {
            log.error("Error while creating NotYetAlignedFromDependencyTree report", e);
            return;
        } finally {
            if (file != null) {
                file.flush();
                file.close();
            }
        }
    }
}
