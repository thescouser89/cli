/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.bacon.da;

import lombok.extern.slf4j.Slf4j;
import org.jboss.bacon.da.rest.endpoint.DAReportsService;
import org.jboss.da.model.rest.GAV;
import org.jboss.da.model.rest.NPMPackage;
import org.jboss.da.reports.model.request.LookupGAVsRequest;
import org.jboss.da.reports.model.request.LookupNPMRequest;
import org.jboss.da.reports.model.response.LookupReport;
import org.jboss.da.reports.model.response.NPMLookupReport;
import org.jboss.pnc.bacon.common.ObjectHelper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import retrofit2.Call;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com <br>
 *         Date: 12/13/18
 */
@Command(name = "da", description = "Dependency Analysis related commands", subcommands = { Da.Lookup.class })
@Slf4j
public class Da {
    @Command(name = "lookup", description = "lookup available productized artifact version for an artifact")
    public static class Lookup implements Callable<Integer> {

        public Lookup() {
        }

        @Parameters(description = "groupId:artifactId:version of the artifact to lookup")
        private String gav = "";

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         */
        @Override
        public Integer call() {

            DAReportsService reports = DAReportsService.getClient();

            List<GAV> gavs = new LinkedList<>();
            GAV gav = new GAV("xom", "xom", "1.2.5");
            gavs.add(gav);
            List<NPMPackage> npmPackageList = new LinkedList<>();
            NPMPackage npmPkg = new NPMPackage("abab", "1.2.3");
            npmPackageList.add(npmPkg);

            LookupNPMRequest npmRequest = LookupNPMRequest.builder().packages(npmPackageList).build();

            LookupGAVsRequest request = new LookupGAVsRequest(
                    Collections.emptySet(),
                    Collections.emptySet(),
                    null,
                    false,
                    null,
                    null,
                    gavs);
            Call<List<LookupReport>> response = reports.lookupGavs(request);
            Call<List<NPMLookupReport>> npmResponse = reports.lookupNPM(npmRequest);
            try {
                ObjectHelper.print(false, response.execute().body());
                ObjectHelper.print(false, npmResponse.execute().body());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return 5;
        }
    }
}
