package com.solution.ntq.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TakeAttendanceRequest {
    int idAttendance;
    String idUser;
    int contentId;
    boolean isAttendance;
}
