package org.moon;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "study_group")
public class StudyGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "studyGroup")
    private List<Student> students;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "groupId=" + groupId +
                ", name='" + name + '\'';
    }
}
