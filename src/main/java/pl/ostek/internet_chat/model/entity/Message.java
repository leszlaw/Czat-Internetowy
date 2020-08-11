package pl.ostek.internet_chat.model.entity;

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
    @ManyToOne
    @JoinColumn(name="receiver_id", nullable=false)
    private User receiver;
    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private User sender;

}
