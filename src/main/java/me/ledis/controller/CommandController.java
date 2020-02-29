package me.ledis.controller;

import me.ledis.CommandService;
import me.ledis.data.LedisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {

    private CommandService service;

    @Autowired
    public CommandController(CommandService service) {
        this.service = service;
    }

    @PostMapping("/run")
    public String run(@RequestBody String command) {
        LedisData.set("1", "Abc");
        return "";
    }
}