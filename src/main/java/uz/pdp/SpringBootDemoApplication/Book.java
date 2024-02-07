package uz.pdp.SpringBootDemoApplication;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private Long id;
    private String title;
    private String description;
    private String author;
    private Long price;
}
