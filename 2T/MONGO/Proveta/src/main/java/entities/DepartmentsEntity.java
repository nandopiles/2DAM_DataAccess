package entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "departments", schema = "act4_1", catalog = "")
public class DepartmentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dept_num", nullable = false)
    private int deptNum;
    @Basic
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Basic
    @Column(name = "office", nullable = true, length = 20)
    private String office;
    @OneToMany(mappedBy = "departmentsByDeptNum")
    private Collection<TeachersEntity> teachersByDeptNum;

    public int getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(int deptNum) {
        this.deptNum = deptNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Collection<TeachersEntity> getTeachersByDeptNum() {
        return teachersByDeptNum;
    }

    public void setTeachersByDeptNum(Collection<TeachersEntity> teachersByDeptNum) {
        this.teachersByDeptNum = teachersByDeptNum;
    }
}
