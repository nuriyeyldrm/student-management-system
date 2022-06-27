package dev.proqa.studentmanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true, nullable = false)
    private Long studentID;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @Column(nullable = false)
    private LocalDateTime enrollTime = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department departmentId;

    public Student(User userId, Department department) {
        this.userId = userId;
        this.departmentId = department;
    }

}
