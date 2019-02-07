package org.jboss.pnc.bacon.cli.pnc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.jboss.pnc.bacon.cli.SubCommandHelper;
import org.jboss.pnc.bacon.config.Config;
import org.jboss.pnc.client.Configuration;
import org.jboss.pnc.client.EnvironmentClient;
import org.jboss.pnc.dto.BuildEnvironment;
import picocli.CommandLine;

@CommandLine.Command(name = "environment", mixinStandardHelpOptions = true)
public class Environment extends SubCommandHelper {

    @CommandLine.Command(name = "get", mixinStandardHelpOptions = true)
    public void get() {

    }

    @CommandLine.Command(name = "list", mixinStandardHelpOptions = true)
    public void list(@CommandLine.Option(names = {"--json"},
                                         description = "Print JSON output (Default: YAML)")
                      boolean jsonOutput) {

        Configuration connectionInfo = Configuration.builder()
                .protocol("http")
                .host(Config.instance().getPnc().getUrl())
                .build();

        ObjectMapper mapper;

        if (jsonOutput) {
            mapper = new ObjectMapper();
        } else {
            mapper = new ObjectMapper(new YAMLFactory());
        }

        EnvironmentClient client = new EnvironmentClient(connectionInfo);

        try {
            for (BuildEnvironment b: client.getAll()) {
                System.out.println(mapper.writeValueAsString(b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
