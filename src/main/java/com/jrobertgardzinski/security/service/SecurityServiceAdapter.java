package com.jrobertgardzinski.security.service;

import com.jrobertgardzinski.security.aggregate.AuthorizedUserAggregateRootEntity;
import com.jrobertgardzinski.security.domain.aggregate.AuthorizedUserAggregate;
import com.jrobertgardzinski.security.domain.entity.AuthorizationData;
import com.jrobertgardzinski.security.domain.entity.User;
import com.jrobertgardzinski.security.domain.repository.UserRepository;
import com.jrobertgardzinski.security.domain.service.SecurityService;
import com.jrobertgardzinski.security.domain.vo.Email;
import com.jrobertgardzinski.security.domain.vo.IpAddress;
import com.jrobertgardzinski.security.domain.vo.Password;
import com.jrobertgardzinski.security.domain.vo.RefreshToken;
import com.jrobertgardzinski.security.entity.AuthorizationDataEntity;
import com.jrobertgardzinski.security.entity.UserEntity;
import com.jrobertgardzinski.security.repository.*;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Singleton;

import java.util.Calendar;

@Introspected(
        classNames = {
                "com.jrobertgardzinski.security.domain.vo.Email",
                "com.jrobertgardzinski.security.domain.vo.Password"
        }
)
@SerdeImport(Calendar.class)
@Singleton
public class SecurityServiceAdapter {
    private final SecurityService securityService;

    public SecurityServiceAdapter(UserJpaRepository userJpaRepository,
                                  AuthorizationDataJpaRepository authorizationDataJpaRepository,
                                  FailedAuthenticationJpaRepository failedAuthenticationJpaRepository,
                                  AuthenticationBlockJpaRepository authorizationDataRepositoryAdapter) {
        this.securityService = new SecurityService(
                new UserRepositoryAdapter(userJpaRepository),
                new AuthorizationDataRepositoryAdapter(authorizationDataJpaRepository),
                new FailedAuthenticationRepositoryAdapter(failedAuthenticationJpaRepository),
                new AuthenticationBlockRepositoryAdapter(authorizationDataRepositoryAdapter)
        );
    }

    public UserEntity register(User user) {
        return UserEntity.fromDomain(securityService.register(user));
    }

    public AuthorizedUserAggregateRootEntity authenticate(IpAddress ipAddress, Email email, Password password) {
        return AuthorizedUserAggregateRootEntity.from(securityService.authenticate(ipAddress, email, password));
    }

    public AuthorizationDataEntity refreshToken(Email email, RefreshToken refreshToken) {
        return AuthorizationDataEntity.fromDomain(securityService.refreshToken(email, refreshToken));
    }
}
