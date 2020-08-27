package com.travisit.travisitstandard.model;

import java.io.Serializable;

public class Language implements Serializable {
    private Integer id = null;
    private String language = null;
    private String languageAR = null;

    public Language(Integer id, String language, String languageAR) {
        this.id = id;
        this.language = language;
        this.languageAR = languageAR;
    }


}
