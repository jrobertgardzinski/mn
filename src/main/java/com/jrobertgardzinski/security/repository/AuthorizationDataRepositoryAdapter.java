package com.jrobertgardzinski.security.repository;

import com.jrobertgardzinski.security.domain.entity.AuthorizationData;
import com.jrobertgardzinski.security.domain.repository.AuthorizationDataRepository;
import com.jrobertgardzinski.security.domain.vo.Email;
import com.jrobertgardzinski.security.domain.vo.RefreshToken;
import com.jrobertgardzinski.security.domain.vo.RefreshTokenExpiration;
import com.jrobertgardzinski.security.domain.vo.TokenExpiration;
import com.jrobertgardzinski.security.entity.AuthorizationDataEntity;
import io.micronaut.core.annotation.NonNull;

import java.util.Optional;

public class AuthorizationDataRepositoryAdapter implements AuthorizationDataRepository {
    private final AuthorizationDataJpaRepository repository;

    public AuthorizationDataRepositoryAdapter(AuthorizationDataJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorizationData create(AuthorizationData authorizationData) {
        return repository.save(AuthorizationDataEntity.fromDomain(authorizationData)).asDomain();
    }

    @Override
    public void deleteBy(Email email) {
        repository.deleteByEmail(email.value());
    }

    @Override
    public RefreshTokenExpiration findRefreshTokenExpirationBy(Email email, RefreshToken refreshToken) {
        return new RefreshTokenExpiration(
                new TokenExpiration(
                        repository.findRefreshTokenExpirationByEmailAndRefreshToken(email.value(), refreshToken.value().value())));
    }

    @Override
    public Optional<AuthorizationData> findBy(Email email) {
        return repository.findById(email.value()).map(AuthorizationDataEntity::asDomain);
    }
}
