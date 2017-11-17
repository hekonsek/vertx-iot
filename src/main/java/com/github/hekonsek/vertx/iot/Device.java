package com.github.hekonsek.vertx.iot;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class Device {

    private String id;

    private String type;

    private Date created;

    private Date updated;

    private Map<String, Object> properties = new LinkedHashMap<>();

}
