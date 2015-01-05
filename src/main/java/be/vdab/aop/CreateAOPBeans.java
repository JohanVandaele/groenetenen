package be.vdab.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// Spring maakt hiermee één bean per class voorzien van @Component in de package be.vdab.aop
@ComponentScan(basePackageClasses = CreateAOPBeans.class)
// Spring AOP werkt enkel als je deze regel opneemt in je Java Config class.
@EnableAspectJAutoProxy
public class CreateAOPBeans
{
}
