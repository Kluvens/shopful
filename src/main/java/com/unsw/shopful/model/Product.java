package com.unsw.shopful.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "products")
public class Product extends AuditTimeMetadata implements Serializable {
    @Id
    private String id;

    private String productName;
}
