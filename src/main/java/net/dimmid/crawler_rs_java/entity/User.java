package net.dimmid.crawler_rs_java.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Table(schema = "npc_crawler", name = "users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;

    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("is_active")
    private boolean active;
    @Column("is_deleted")
    private boolean deleted;
}

