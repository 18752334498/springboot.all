package com.yucong.anno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String age;

}
