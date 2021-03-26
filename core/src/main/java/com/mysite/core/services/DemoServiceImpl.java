package com.mysite.core.services;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = DemoService.class , immediate = true)
@Designate(ocd = DemoServiceConfig.class)
public class DemoServiceImpl implements DemoService{

    private Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    private  DemoServiceConfig config;

    private String password ;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Activate
    @Modified
    public void activate(DemoServiceConfig demoServiceConfig){
        LOGGER.debug("Inside Activate/Modified");
        this.config = demoServiceConfig;
        this.password = demoServiceConfig.password();
    }

    @Deactivate
    protected void deactivate(){
        LOGGER.debug("Inside Deactivate");
    }

    @Override
    public String getName() {
        return config.name();
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
