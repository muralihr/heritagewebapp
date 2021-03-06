package org.janastu.heritageapp.config;

import org.janastu.heritageapp.security.*;
import org.janastu.heritageapp.security.xauth.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;


import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll()
            .antMatchers("/api/account/reset_password/finish").permitAll()
            .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/audits/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/heritageMedias").permitAll()
            .antMatchers("/api/videoGeoTagHeritageEntitys").permitAll()
            .antMatchers("/api/audioGeoTagHeritageEntitys").permitAll()
            .antMatchers("/api/textGeoTagHeritageEntitys").permitAll()
            .antMatchers("/api/imageGeoTagHeritageFromMobile").permitAll()
            .antMatchers("/api/videoGeoTagHeritageFromMobile").permitAll()
            .antMatchers("/api/audioGeoTagHeritageFromMobile").permitAll()
            .antMatchers("/api/textGeoTagHeritageFromMobile").permitAll()
            .antMatchers("/api/registerForMobile").permitAll()
            .antMatchers("/api/imageGeoTagHeritageEntitysGeoJson").permitAll()
            .antMatchers("/api/allGeoTagHeritageEntitysGeoJson").permitAll()
            .antMatchers("/api/mapp/**").permitAll()
            .antMatchers("/api/mapp/app/*").permitAll()
            .antMatchers("/api/mapplist/**").permitAll()            
            .antMatchers("/api/mapplist/app/*").permitAll()
            
            .antMatchers("/api/users/*").permitAll()
            // /added to support retreiving groups/regions and languages
            .antMatchers("/api/heritageCategorys2").permitAll()
            .antMatchers("/api/heritageRegionNames2").permitAll()
            .antMatchers("/api/heritageGroups2").permitAll()
            .antMatchers("/api/heritageLanguages2").permitAll()

            
            .antMatchers("/api/heritageCategorys").permitAll()
            .antMatchers("/api/heritageLanguages").permitAll()
            .antMatchers("/api/createAnyMediaGeoTagHeritageFromMobile").permitAll()
            .antMatchers("/api/createAnyMediaGeoTagHeritageFromWeb").permitAll()
            .antMatchers("/api/createAnyMediaGeoTagHeritageFromMobile2").permitAll()
            .antMatchers("/api/createNewMediaHeritageForm/app/**").permitAll()
            .antMatchers("/api/createNewMediaHeritageForm2/**").permitAll()
            .antMatchers("/api/editNewMediaHeritageForm2/**").permitAll()
            .antMatchers("/api/deleteNewMediaHeritageForm2/**").permitAll()
            .antMatchers("/api/getCurrentStorageSize/user/**").permitAll()
            //getCurrentStorageSize
            ///permission api/getUserGroups/user/userid(str) with auth 
            
            .antMatchers("/api/registerToGroup/**").permitAll()
            .antMatchers("/api/getUserGroups/**").permitAll()
            
            //createNewMediaHeritageForm //
            .antMatchers("/api/createMediaHeritageForm/**").permitAll()
            .antMatchers("/api/getAppConfigInfo/**").permitAll()
            .antMatchers("/api/addUserToGroup/**").permitAll()
            .antMatchers("/api/heritageAppsMob/**").permitAll()
            ////user/
            .antMatchers("/api/getUserGroups/**").permitAll()
            
            //.antMatchers("/api/addUserToGroup/**").hasAuthority(AuthoritiesConstants.USER)
            //createAnyMediaGeoTagHeritageFromMobile2 ///addUserToGroup/
            
            
            .antMatchers("/api/**").authenticated()
          
            .antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/mappings/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/liquibase/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**").authenticated() 
            .and()
            .apply(securityConfigurerAdapter());

    }

    private XAuthTokenConfigurer securityConfigurerAdapter() {
      return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
