package net.dimmid.crawler_rs_java.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Data
@Table(schema = "npc_crawler", name = "characters")
public class Character {
    private Long id;
    private String name;

    @Column("user_id")
    private String userId;
}

