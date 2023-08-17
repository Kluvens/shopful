package com.unsw.shopful.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AuditTimeMetadata {
    
    @CreatedDate
    protected Date createdDate;

    @LastModifiedDate
    protected Date lastModifiedDate;
}
