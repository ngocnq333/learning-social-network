package com.solution.ntq.controller;

import com.solution.ntq.model.Clazz;
import com.solution.ntq.service.IClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RestController
public class ClazzController {


    private IClazzService iClazzService;

    /**
     * fix data of application
     * @return
     * @throws ParseException
     */
    @GetMapping(path = "/configure-data")
    public ResponseEntity<String> getData() throws ParseException {
        iClazzService.addAllData();
        return new ResponseEntity<>("Add ok", HttpStatus.OK);

    }

    @GetMapping(path="/api/v1/classes/{user_id}")
    public ResponseEntity<List<Clazz>> getClassFollowingByUser(@PathVariable("user_id") String userId) {
        List<Clazz> clazzList= iClazzService.getClassByUser(userId);
        return new ResponseEntity<>(clazzList, HttpStatus.OK);
    }

    @GetMapping(path="/api/v1/class/{class_id}")
    public ResponseEntity<Clazz> getClassById(@PathVariable("class_id") int clazzId) {
        Clazz clazz= iClazzService.getClassById(clazzId);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }


}
