package eu.deyanix.smartirrigation.configurator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfigurator  {
	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration()
		configuration.addAllowedOrigin("http://localhost:9000")
		configuration.addAllowedMethod("*")
		configuration.addAllowedHeader("*")

		val source = UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", configuration)
		return source
	}

	@Bean
	fun chain(http: HttpSecurity) : SecurityFilterChain {
		return http
			.cors(Customizer.withDefaults())
			.csrf {
				it.disable()
			}
			.authorizeHttpRequests {
				it.anyRequest().permitAll()
			}
			.httpBasic {
				it.disable()
			}
			.build()
	}
}
