package com.jrobertgardzinski.security;

import com.jrobertgardzinski.security.aggregate.AuthorizedUserAggregateRootEntity;
import com.jrobertgardzinski.security.domain.aggregate.AuthorizedUserAggregate;
import com.jrobertgardzinski.security.domain.entity.AuthorizationData;
import com.jrobertgardzinski.security.domain.entity.User;
import com.jrobertgardzinski.security.domain.vo.*;
import com.jrobertgardzinski.security.entity.AuthorizationDataEntity;
import com.jrobertgardzinski.security.entity.UserEntity;
import com.jrobertgardzinski.security.service.SecurityServiceAdapter;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.util.HttpClientAddressResolver;

@Controller
public class DefaultController {
    private final SecurityServiceAdapter service;
    private final HttpClientAddressResolver addressResolver;

    public DefaultController(SecurityServiceAdapter service, HttpClientAddressResolver addressResolver) {
        this.service = service;
        this.addressResolver = addressResolver;
    }

    @Post(uri="register")
    public UserEntity register(String email, String password) {
        return service.register(new User(() -> new Email(email), () -> new Password(password)));
    }

    @Post(uri="authenticate")
    public AuthorizedUserAggregateRootEntity authenticate(HttpRequest<?> httpRequest, String email, String password) {
        return service.authenticate(new IpAddress(addressResolver.resolve(httpRequest)), new Email(email), new Password(password));
    }

    @Post(uri="refresh")
    public AuthorizationDataEntity refreshToken(String email, String refreshToken) {
        return service.refreshToken(new Email(email), new RefreshToken(new Token(refreshToken)));
    }
}