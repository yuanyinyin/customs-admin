package com.nteport.admin.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Dept4AppDTO {
    private String name;
    private Long id;
    private boolean tree;
    private List<Dept4AppDTO> children = new ArrayList<Dept4AppDTO>();
}
