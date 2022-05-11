package com.springheroes.wellco.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
}
