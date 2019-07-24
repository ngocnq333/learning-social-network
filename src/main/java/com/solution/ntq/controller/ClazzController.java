package com.solution.ntq.controller;

import com.solution.ntq.model.Clazz;
import com.solution.ntq.response.Response;
import com.solution.ntq.service.base.IClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1/classes")
public class ClazzController {
    private IClazzService iClazzService;

    /**
     * fix data of application
     *
     * @return
     * @throws ParseException
     */


    @GetMapping
    public ResponseEntity<Response<List<Clazz>>> getClassFollowingByUser(@RequestParam("userId") String userId) throws ParseException {
        List<Clazz> clazzList = iClazzService.getClassByUser(userId);
        Response<List<Clazz>> listResponse = new Response<>(HttpStatus.OK, clazzList);
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    @GetMapping("/classes/{class_id}")
    public ResponseEntity<Response<Clazz>> getClassById(@PathVariable("class_id") int clazzId) {
        Clazz clazz = iClazzService.getClassById(clazzId);
        Response<Clazz> listResponse = new Response<>(HttpStatus.OK, clazz);
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }
}
