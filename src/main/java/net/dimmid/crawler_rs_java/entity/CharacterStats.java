package net.dimmid.crawler_rs_java.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Data
@Table(schema = "npc_crawler", name = "char_stats")
public class CharacterStats {
    private Integer id;
    @Column("char_id")
    private Integer charId;
    private Integer health;
    private Integer energy;
    private Integer hungry;
    @Column("position_x")
    private Integer positionX;
    @Column("position_y")
    private Integer positionY;
    @Column("location_id")
    private Integer locationId;
    @Column("attack_modifier")
    private Integer attackModifier;
    @Column("attackDamage")
    private Integer attackDamage;
    private Integer defence;
    @Column("is_dead")
    private Boolean dead;
    @Column("is_sleep")
    private Boolean sleep;
}