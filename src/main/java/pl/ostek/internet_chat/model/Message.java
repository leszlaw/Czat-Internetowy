package pl.ostek.internet_chat.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Message {

    private final String message;
    private final String receiverId;
    private String senderId;

}
