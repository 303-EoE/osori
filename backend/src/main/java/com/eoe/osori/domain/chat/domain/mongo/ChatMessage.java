package com.eoe.osori.domain.chat.domain.mongo;


import com.eoe.osori.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "chat_message")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Long chatRoomId;

    @NotNull
    @Column
    private Long senderId;

    @NotNull
    @Column
    private String senderNickname;

    @NotNull
    @Column
    private String senderProfileImageUrl;

    @NotNull
    @Column
    // 빈값이면 처리 해주기!! -> 프론트한테 말해야겠다.
    private String content;

    @Column(columnDefinition = "int default 2")
    private int readCount;

}
