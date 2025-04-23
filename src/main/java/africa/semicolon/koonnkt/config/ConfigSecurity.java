
package africa.semicolon.koonnkt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Component
public class ConfigSecurity {


    private final UserDetailsService userDetailsService;

    public ConfigSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("api/users/register").permitAll()
                        .requestMatchers("api/users/login").permitAll()
                        .requestMatchers("api/PostRide/createRide").hasRole("DRIVER")
                        .requestMatchers("api/rideRequest/createRideRequest").hasRole("PASSENGER")
                        .requestMatchers("api/PostRide/driver").hasRole("DRIVER")
                        .requestMatchers("api/PostRide/updateRide/").hasRole("DRIVER")
                        .requestMatchers("api/PostRide/deleteRide/").hasRole("DRIVER")
                        .requestMatchers("api/rideRequest/updateRideRequest/").hasRole("PASSENGER")
                        .requestMatchers("api/rideRequest/passenger").hasRole("PASSENGER")
                        .requestMatchers("api/users/blockUser").hasRole("ADMIN")
                        .requestMatchers("api/users/unblockUser").hasRole("ADMIN")
                        .requestMatchers("api/users/updateUserDetail/").permitAll()
                        .requestMatchers("api/report/makeReport").hasAnyRole("PASSENGER", "DRIVER")
                        .requestMatchers("/initialize").permitAll()
                        .requestMatchers("/makePayment").permitAll()
                        .requestMatchers("/verify/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(corsFilter(), SessionManagementFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://koonnkt.vercel.app");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Value("${paystack.api.key}")
    private String payStackApi;




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
