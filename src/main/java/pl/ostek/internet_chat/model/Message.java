package pl.ostek.internet_chat.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String message;
    @Column(name = "receiver_id")
    private String receiverId;
    @Column(name = "sender_id")
    private String senderId;

}
