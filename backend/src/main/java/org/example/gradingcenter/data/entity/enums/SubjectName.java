package org.example.gradingcenter.data.entity.enums;

public enum SubjectName {
    MATHEMATICS("Mathematics"),
    ENGLISH("English"),
    BIOLOGY("Biology"),
    CHEMISTRY("Chemistry"),
    PHYSICS("Physics");

    private final String capitalizedName;

    SubjectName(String capitalizedName) {
        this.capitalizedName = capitalizedName;
    }

    @Override
    public String toString() {
        return capitalizedName;
    }
}
