package com.semestral.requests;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CourseManagementRequest {

    private String title;
    private String description;
    private String category;
    private String level;
    private Date dateCreated;
    private Date lastUpdated;
    private String thumbnail;
    private String userID;
    private String id;

    private String enrollTemp;

}
