package com.example.oneulnail.domain.likeStyle.entity;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "like_style")
public class LikeStyle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "style1") // 스타일 1
    private String style1;

    @Column(name = "style2") // 스타일 2
    private String style2;

    @Column(name = "style3") // 스타일 3
    private String style3;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public LikeStyle(String style1, String style2, String style3, User user){
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.user = user;
    }
}
