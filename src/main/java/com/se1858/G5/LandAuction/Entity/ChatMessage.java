package com.se1858.G5.LandAuction.Entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messId;
    private String content;
    private Date timestamp;
    private String sender;
    private String receiver;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom room;
}
