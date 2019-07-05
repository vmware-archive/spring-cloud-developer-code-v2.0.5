package io.pivotal.pal.tracker.timesheets;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectClient {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final Map<Long, ProjectInfo> projectInfoCache;

    private final RestOperations restOperations;
    private final String endpoint;

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
        this.projectInfoCache = new ConcurrentHashMap<>();
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        logger.info("Obtained and cached projectInfo for projectId '{}'", projectId);

        ProjectInfo projectInfo = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);
        projectInfoCache.put(projectId,projectInfo);

        return projectInfo;
    }

    public ProjectInfo getProjectFromCache(long projectId) {
        logger.info("Fell back to using cached project lookup for projectId '{}'", projectId);

        return projectInfoCache.get(projectId);
    }
}
