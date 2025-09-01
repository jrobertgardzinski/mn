package com.jrobertgardzinski.security.entity;

import com.jrobertgardzinski.security.domain.entity.FailedAuthentication;
import com.jrobertgardzinski.security.domain.vo.FailedAuthenticationDetails;
import com.jrobertgardzinski.security.domain.vo.FailedAuthetincationId;
import com.jrobertgardzinski.security.domain.vo.IpAddress;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Calendar;

@Serdeable
@Entity
public class FailedAuthenticationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ipAddress;
    private Calendar time;

    public FailedAuthenticationEntity() {
    }

    public FailedAuthenticationEntity(String ipAddress, Calendar time) {
        this.ipAddress = ipAddress;
        this.time = time;
    }

    public FailedAuthenticationEntity(long id, String ipAddress, Calendar time) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.time = time;
    }

    public static FailedAuthenticationEntity fromDomain(FailedAuthentication failedAuthentication) {
        return new FailedAuthenticationEntity(
                failedAuthentication.id().value(),
                failedAuthentication.details().ipAddress().value(),
                failedAuthentication.details().time()
        );
    }

    public FailedAuthentication asDomain() {
        return new FailedAuthentication(
                new FailedAuthenticationDetails(
                        new IpAddress(ipAddress),
                        time
                ),
                new FailedAuthetincationId(id)
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
