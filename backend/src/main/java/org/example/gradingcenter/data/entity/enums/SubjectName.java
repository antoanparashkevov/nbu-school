package org.example.gradingcenter.data.entity.enums;

public enum SubjectName {

    MATHEMATICS("Mathematics"),
    LITERATURE("Literature"),
    GEOGRAPHY("Geography"),
    PHILOSOPHY("Philosophy"),
    DRAWING("Drawing"),
    MUSIC("Music"),
    BULGARIAN("Bulgarian"),
    RUSSIAN("Russian"),
    GERMAN("German"),
    ENGLISH("English"),
    FRENCH("French"),
    SPANISH("Spanish"),
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
