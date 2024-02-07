package uz.pdp.SpringBootDemoApplication;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private Long id;
    private String name;
    private String author;
}
