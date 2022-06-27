package com.camunda.example.config;

import com.camunda.example.custom.CustomProcessEngineRestServiceImpl;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.engine.rest.impl.JaxRsTwoDefaultProcessEngineRestServiceImpl;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import java.util.Set;
/*
 * CamundaBpmRestJerseyAutoConfiguration will create CamundaJerseyResourceConfig
 * bean only ConditionalOnMissingBean. We create a matching bean which will be used instead.
 */

@Component
@ApplicationPath("/engine-rest")
public class CustomCamundaJerseyResourceConfig extends CamundaJerseyResourceConfig {

  @Override
  protected void registerCamundaRestResources() {
    Set<Class<?>> resourceClasses = CamundaRestResources.getResourceClasses();
    // remove original JaxRsTwoDefaultProcessEngineRestServiceImpl
    resourceClasses.remove(JaxRsTwoDefaultProcessEngineRestServiceImpl.class);
    // add modified version extending original
    resourceClasses.add(CustomProcessEngineRestServiceImpl.class
    );
    // perform same as registrations as CamundaJerseyResourceConfig
    this.registerClasses(resourceClasses);
    this.registerClasses(CamundaRestResources.getConfigurationClasses());
    this.register(JacksonFeature.class);
  }
}
