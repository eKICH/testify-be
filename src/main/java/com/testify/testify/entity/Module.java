package com.testify.testify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"application", "parentModule"}) // avoid recursive toString on self-reference
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "parent_module_id")
    private Module parentModule; // null = top-level module

    // Materialized path, e.g. "/12/47/103/" - built from each ancestor's id.
    // Cannot be computed until this row has an id, so it's set to "" on
    // initial insert and patched in a second write by ModuleService. See
    // ModuleServiceImpl#createModule for why (IDENTITY ids aren't known
    // client-side before insert, unlike the UUID-based design this replaced).
    @Column(nullable = false, length = 2000)
    private String path = "";

    @Column(nullable = false)
    private Integer depth = 0;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Explicit Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    public Module getParentModule() { return parentModule; }
    public void setParentModule(Module parentModule) { this.parentModule = parentModule; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
