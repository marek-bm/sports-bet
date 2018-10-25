package pl.coderslab.sportsbet2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.coderslab.sportsbet2.converter.DateConverter;

//@EnableWebMvc
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter  {

    @Bean
    public DateConverter getDateConverter() {
        return new DateConverter();
    }


    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(getDateConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/img/**",
                "/css/**",
                "/js/**",
                "/vendor/**",
                "/fixture-stats/vendor/**",
                "/fixture-stats/css/**",
                "/user-edit/vendor/**",
                "/user-edit/**",
                "/user-edit/css/**",
                "/user-edit/js/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/vendor/");
    }

}
