package wendy.study.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(RestfulWebServicesApplication.class, args);

		String[] allBeanNames = ac.getBeanDefinitionNames();
		for(String beanName : allBeanNames)
			System.out.println(beanName);
	}

}
