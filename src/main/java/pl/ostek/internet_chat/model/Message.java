package pl.ostek.internet_chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String message;
    @Column(name = "receiver_id")
    private String receiverId;
    @Column(name = "sender_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String senderId;

}
