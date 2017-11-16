package com.github.hekonsek.vertx.iot;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Device {

    private String id;

    private String type;

    private Date created;

}
