package org.jboss.pnc.bacon.cli.pnc;

import org.jboss.pnc.bacon.cli.SubCommandHelper;
import org.jboss.pnc.client.Configuration;
import org.jboss.pnc.client.EnvironmentClient;
import org.jboss.pnc.dto.BuildEnvironment;
import picocli.CommandLine;

@CommandLine.Command(name = "environment", mixinStandardHelpOptions = true)
public class Environment extends SubCommandHelper {

    @CommandLine.Command(name = "get", mixinStandardHelpOptions = true)
    public void get() {

        Configuration connectionInfo = Configuration.builder()
                .protocol("http")
                .host("orch-master-devel.psi.redhat.com")
                .pageSize(2)
                .build();

        EnvironmentClient client = new EnvironmentClient(connectionInfo);

        try {
            for (BuildEnvironment b: client.getAll()) {
                System.out.println(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @CommandLine.Command(name = "list", mixinStandardHelpOptions = true)
    public void list() {

    }

}
