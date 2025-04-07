package app.category.model;


import app.thread.model.Thread;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cat_name", unique = true, nullable = false)
    private String categoryName;

    @Column(name = "cat_colour")
    private String categoryColour;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Thread> threads;
}
