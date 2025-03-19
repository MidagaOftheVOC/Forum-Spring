package app.user.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)
    private String userhash;

    @Column
    private String shownUsername;

    @Column
    private String avatarUrl;

    @Column
    private String quote;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private int totalPosts;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime lastRedactDate;

    @Column
    private LocalDateTime lastActiveDate;


    /*
    * Here go the relations, i.e. LIsts for threads and posts
    * */

}
