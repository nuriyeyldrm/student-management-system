package dev.proqa.studentmanagementsystem.entities;

import dev.proqa.studentmanagementsystem.entities.enumeration.UserRole;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name ="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole userRole;



}
