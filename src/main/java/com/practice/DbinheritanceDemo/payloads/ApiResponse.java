package com.practice.DbinheritanceDemo.payloads;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ApiResponse {

    private String Message;
    private boolean success;

}
