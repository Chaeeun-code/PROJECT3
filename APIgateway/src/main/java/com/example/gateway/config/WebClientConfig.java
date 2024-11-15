package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import javax.net.ssl.SSLException;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() throws SSLException {
        // SslContextBuilder에 신뢰할 수 있는 모든 인증서를 수락하는 설정 적용
        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE);

        HttpClient httpClient = HttpClient.create()
                .secure(sslSpec -> {
					try {
						sslSpec.sslContext(sslContextBuilder.build());
					} catch (SSLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
