package pl.coderslab.sportsbet2.appConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.coderslab.sportsbet2.utils.DateConverter;

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
                "/static/img/**",
                "/static/vendor/scss/mixins/**",
                "/fixture-stats/vendor/**",
                "/fixture-stats/css/**",
                "/fixture-stats/static/img/**",
                "/fixture-stats/fonts/icomoon/**",
                "/fixture-stats/img/**",
                "/user-edit/vendor/**",
                "/user-edit/**",
                "/user-edit/css/**",
                "/user-edit/js/**",
                "/fixture-edit/vendor/**",
                "/fixture-edit/css/**",
                "/fixture-edit/static/img/**",
                "/fixture-edit/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/vendor/",
                        "classpath:/static/vendor/scss/mixins/");
    }

}
