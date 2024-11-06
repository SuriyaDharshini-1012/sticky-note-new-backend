package com.stickynotes.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="note")
public class Note
{
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String id;

    @ManyToOne
    private User user;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="colour")
    private String colour;

    @Column(name="is_pinned")
    private Boolean isPinned;

    @Column(name = "created_at")
    @CurrentTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
