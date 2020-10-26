package org.jboss.pnc.bacon.pig;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PigFacadeTest {
    static final Path testDirs = Paths.get("src", "test", "resources", "pre-process-yaml");

    /**
     * Test if variable substitution works
     */
    @Test
    void preProcessYamlVariableSubstitution() throws Exception {
        String content = PigFacade.preProcessYaml(testDirs.resolve("vars.yaml").toFile(), Collections.emptyMap());
        String expected = Files.asCharSource(testDirs.resolve("vars-complete.yaml").toFile(), StandardCharsets.UTF_8)
                .read()
                .trim();
        assertEquals(expected, content.trim());
    }

    /**
     * Test if variable substitution works with external overrides
     */
    @Test
    void preProcessYamlVariableSubstitutionWithOverrides() throws Exception {

        Map<String, String> overrides = ImmutableMap.of("variable", "hola", "foo", "baz");
        String content = PigFacade.preProcessYaml(testDirs.resolve("vars.yaml").toFile(), overrides);
        String expected = Files
                .asCharSource(testDirs.resolve("vars-complete-overrides.yaml").toFile(), StandardCharsets.UTF_8)
                .read()
                .trim();
        assertEquals(expected, content.trim());
    }

    /**
     * Test if variable substitution works with variable defined in override only
     */
    @Test
    void preProcessYamlVariableSubstitutionWithOverrideOnly() throws Exception {

        Map<String, String> overrides = ImmutableMap.of("variable_in_overrides", "lisa");
        String content = PigFacade.preProcessYaml(testDirs.resolve("no-vars.yaml").toFile(), overrides);
        String expected = Files
                .asCharSource(testDirs.resolve("no-vars-complete-overrides.yaml").toFile(), StandardCharsets.UTF_8)
                .read()
                .trim();
        assertEquals(expected, content.trim());
    }
}
