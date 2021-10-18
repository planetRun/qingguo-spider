package org.choviwu.top.qg.entity;

import lombok.Data;

import java.util.List;

@Data
public class CourseScoreDTO {

    private String key;

    private List<CourseScore> list;
}
