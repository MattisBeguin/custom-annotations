package com.saintquentin.academy.customannotations.controllers;

import com.saintquentin.academy.customannotations.annotations.reflections.ObjectToJsonConverter;
import com.saintquentin.academy.customannotations.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/v1/annotation")
public class AnnotationController {
    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        try {
            Person person = new Person("tellier", "romain", "23");
            ObjectToJsonConverter objectToJsonConverter = new ObjectToJsonConverter();
            String convertToJson = objectToJsonConverter.convertToJson(person);

            return new ResponseEntity<>(convertToJson, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(Arrays.toString(exception.getStackTrace()));

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
