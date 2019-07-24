package com.solution.ntq.controller;

import com.solution.ntq.model.Clazz;
import com.solution.ntq.service.base.IClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RestController
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1")
public class ClazzController {


    private IClazzService iClazzService;

    /**
     * fix data of application
     * @return
     * @throws ParseException
     */


    @GetMapping("/classes/users/{user-id}")
    public ResponseEntity<List<Clazz>> getClassFollowingByUser(@PathVariable("user-id") String userId) throws ParseException {

        List<Clazz> clazzList= iClazzService.getClassByUser(userId);
        return new ResponseEntity<>(clazzList, HttpStatus.OK);
    }

    @GetMapping("/classes/{class_id}")
    public ResponseEntity<Clazz> getClassById(@PathVariable("class_id") int clazzId) {
        Clazz clazz= iClazzService.getClassById(clazzId);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }


}
