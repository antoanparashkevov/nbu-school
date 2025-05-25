package org.example.gradingcenter.data.entity.enums;

public enum Roles {

    ROLE_STUDENT("Student"),
    ROLE_PARENT("Parent"),
    ROLE_TEACHER("Teacher"),
    ROLE_HEADMASTER("Headmaster"),
    ROLE_ADMIN("Admin");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public static Roles[] valuesWithoutAdmin() {
        return new Roles[]{ROLE_HEADMASTER, ROLE_STUDENT, ROLE_TEACHER, ROLE_PARENT};
    }

    @Override
    public String toString() {
        return roleName;
    }

}
