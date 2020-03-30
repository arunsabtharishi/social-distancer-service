package com.socialdistancer.model;


import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@RedisHash("LocationDetails")
public class LocationDetails implements Serializable {
    private static final long serialVersionUID = 1603714798906422731L;
    String id;
    String latitude;
    String longitude;
    String altitude;
}
