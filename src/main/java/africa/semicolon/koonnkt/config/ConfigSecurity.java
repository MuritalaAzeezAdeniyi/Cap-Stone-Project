package africa.semicolon.koonnkt.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {
    @Autowired
  private  UserDetailsService userDetailsService;

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
                             .anyRequest().authenticated())
                     .httpBasic(Customizer.withDefaults())
                     .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

             return  http.build();
    }

//    @Bean
//   public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails passenger = User.withDefaultPasswordEncoder()
//                .username("passenger")
//                .password("password")
//                .roles("PASSENGER")
//                .build();
//
//        UserDetails driver = User.withDefaultPasswordEncoder()
//                .username("driver")
//                .password("password")
//                .roles("Driver")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,passenger,driver);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
             authProvider.setUserDetailsService(userDetailsService);
             authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
