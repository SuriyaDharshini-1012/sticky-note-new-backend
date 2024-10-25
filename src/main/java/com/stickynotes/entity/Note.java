package com.stickynotes.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.awt.*;
import java.util.Date;

@Builder
@Data
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
