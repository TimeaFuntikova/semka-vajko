package com.semestral.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequest {

    private String title;
    private String content;
    private String videoURL;
    private String additional_resources;
    private String sequence;
    private String created_at;
    private String updated_at;
    private String user_id;
    private String course_id;

    private String quiz_question;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String correct_answer_index;
}
