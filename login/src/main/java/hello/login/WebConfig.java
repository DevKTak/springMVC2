package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * 인터셉터 등록 (implements WebMvcConfigurer)
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
       .order(1)
       .addPathPatterns("/**")
       .excludePathPatterns("/css/**", "*.ico", "/error");

    registry.addInterceptor(new LoginCheckInterceptor())
       .order(2)
       .addPathPatterns("/**") // 모든 경로를 체크하는데
       .excludePathPatterns("/", "/members/add", "/login", "/logout",
          "/css/**", "/*.icon", "/error"); // 이 패턴은 빼주겠다
  }

  /**
   * 필터 등록
   */
//  @Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.setOrder(1); // 순서
    filterRegistrationBean.addUrlPatterns("/*");

    return filterRegistrationBean;
  }

//  @Bean
  public FilterRegistrationBean loginCheckFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LoginCheckFilter());
    filterRegistrationBean.setOrder(2); // 순서
    filterRegistrationBean.addUrlPatterns("/*");

    return filterRegistrationBean;
  }
}
