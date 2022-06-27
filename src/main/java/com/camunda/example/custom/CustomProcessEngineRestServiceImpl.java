package com.camunda.example.custom;

import org.camunda.bpm.engine.rest.DeploymentRestService;
import org.camunda.bpm.engine.rest.impl.DeploymentRestServiceImpl;
import org.camunda.bpm.engine.rest.impl.JaxRsTwoDefaultProcessEngineRestServiceImpl;

import javax.ws.rs.Path;

public class CustomProcessEngineRestServiceImpl extends JaxRsTwoDefaultProcessEngineRestServiceImpl {

  // Override in order to use own DeploymentService instead of DeploymentRestServiceImpl
  @Override
  @Path(DeploymentRestService.PATH)
  public DeploymentRestService getDeploymentRestService() {
    String engineName = null;
    String rootResourcePath = getRelativeEngineUri(engineName).toASCIIString();
    DeploymentRestServiceImpl subResource = new RestrictedDeploymentRestServiceImpl(engineName, getObjectMapper());
    subResource.setRelativeRootResourceUri(rootResourcePath);
    return subResource;
  }
}
