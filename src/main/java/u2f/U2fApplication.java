package u2f;

import com.yubico.u2f.U2F;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class U2fApplication {

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public U2F u2f() {
        return new U2F();
    }

    @Bean
    public InMemoryRequestStorage requestStorage() {
        return new InMemoryRequestStorage();
    }

    @Bean
    InMemoryDeviceRegistration deviceRegistration() {
        return new InMemoryDeviceRegistration();
    }
    

    public static void main(String[] args) {
        SpringApplication.run(U2fApplication.class, args);
    }
}
