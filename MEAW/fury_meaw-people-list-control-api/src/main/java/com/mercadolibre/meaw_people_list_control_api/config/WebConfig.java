package com.mercadolibre.meaw_people_list_control_api.config;

import com.mercadolibre.routing.RoutingFilter;
import java.util.Locale;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Gets the router filter.
   * 
   * @return the router filter
   */
  @Bean
  public FilterRegistrationBean<RoutingFilter> routingFilter() {
    FilterRegistrationBean<RoutingFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setName("routingFilter");
    registrationBean.setFilter(new RoutingFilter());
    registrationBean.setOrder(1);
    return registrationBean;
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new FixedLocaleResolver(Locale.US);
  }

}
