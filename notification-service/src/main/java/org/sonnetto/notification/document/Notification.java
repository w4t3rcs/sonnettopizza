package org.sonnetto.notification.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
@Document(indexName = "notification")
public class Notification implements Serializable {
    @Id
    private Long id;
    private Message message;
    private LocalDateTime sentDate;
}
