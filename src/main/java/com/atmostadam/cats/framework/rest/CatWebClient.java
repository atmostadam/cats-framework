package com.atmostadam.cats.framework.rest;

import com.atmostadam.cats.api.configuration.CatWebClientProperties;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.NonNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.reactive.function.client.WebClient.*;

@Service
public class CatWebClient {
    private static final Logger logger = LoggerFactory.getLogger(CatWebClient.class);
    private static final ObjectMapper om = new ObjectMapper();

    public ResponseEntity<CatResponse> invoke(@NonNull CatWebClientProperties properties,
                                              @NonNull String requestId,
                                              @NonNull CatRequest request) {
        String requestStr;
        try {
            requestStr = om.writeValueAsString(request);
        } catch (Exception e) {
            return new CatResponse()
                    .setMessage("Unable to parse request to convert to String with message [" + e.getMessage() + "]")
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .addCats(request.getCats())
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        }

        if(Objects.isNull(properties)) {
            return new CatResponse()
                    .setMessage("No such bean CatWebClientProperties. This bean is required to source url, username, timeouts, etc.")
                    .setStackTrace(ExceptionUtils.getStackTrace(new NoSuchBeanDefinitionException("CatWebClientProperties")))
                    .addCats(request.getCats())
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Objects.requireNonNull(properties.getConnectionTimeout()))
                .responseTimeout(Duration.ofMillis(Objects.requireNonNull(properties.getResponseTimeout())))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(Objects.requireNonNull(properties.getReadTimeout()), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(Objects.requireNonNull(properties.getWriteTimeout()), TimeUnit.MILLISECONDS)));

        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        UriSpec<RequestBodySpec> uriSpec;
        if(properties.getMethod().equalsIgnoreCase("POST")) {
            uriSpec = client.post();
        } else {
            throw new NullPointerException();
        }

        RequestBodySpec bodySpec = uriSpec.uri(Objects.requireNonNull(properties.getUrl()));

        RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(requestStr);

        ResponseEntity<CatResponse> response = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("requestId", requestId)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(CatResponse.class)
                .block();

        return response;
    }
}
