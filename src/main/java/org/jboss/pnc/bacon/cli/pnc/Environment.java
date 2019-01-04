package org.jboss.pnc.bacon.cli.pnc;

import org.jboss.pnc.dto.BuildEnvironment;
import org.jboss.pnc.dto.response.Page;
import org.jboss.pnc.rest.api.endpoints.EnvironmentEndpoint;
import org.jboss.pnc.rest.api.parameters.PageParameters;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import picocli.CommandLine;



@CommandLine.Command(name = "environment", mixinStandardHelpOptions = true)
public class Environment {

    @CommandLine.Command(name = "get", mixinStandardHelpOptions = true)
    public void get() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(ResteasyJackson2Provider.class);
        ResteasyWebTarget target = client.target("http://orch-master-devel.upshift.redhat.com/pnc-rest/rest");

        EnvironmentEndpoint endpoint = target.proxy(EnvironmentEndpoint.class);

        PageParameters params = new PageParameters();
        params.setPageIndex(0);
        params.setPageSize(200);

        Page<BuildEnvironment> response = endpoint.getAll(params);

        response.getContent().stream().forEach(a -> System.out.println(a.getDescription()));
    }

    @CommandLine.Command(name = "list", mixinStandardHelpOptions = true)
    public void list() {

    }

}
