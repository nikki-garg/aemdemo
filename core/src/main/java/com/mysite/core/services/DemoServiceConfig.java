package com.mysite.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Demo Config")
public @interface DemoServiceConfig {

    @AttributeDefinition(name = "Name" , description = "Name of the service" ,type= AttributeType.STRING)
    public String name() default "Nikita";

    @AttributeDefinition(name="Password",description = "Account Password" , type = AttributeType.PASSWORD)
    public String password() default "Nikita";
}
