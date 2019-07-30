package com.yucong.curator;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Person {

    @ZkNode(nodePath = "/person/name", isWather = true, defaultValue = "王大锤")
    private String name;

    @ZkNode(nodePath = "/person/intro", isWather = true, defaultValue = "I am the King, you are the queue.")
    private String intro;
}
