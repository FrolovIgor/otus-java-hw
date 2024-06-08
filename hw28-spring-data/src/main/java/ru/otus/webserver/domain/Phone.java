package ru.otus.webserver.domain;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone")
@Getter
@Setter
public class Phone {
    @Id
    private Long id;

    private String number;

    @Nonnull
    private Long clientId;



    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(String number) {
        this.number = number;
    }
}
