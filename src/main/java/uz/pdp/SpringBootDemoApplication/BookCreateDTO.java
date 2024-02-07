package uz.pdp.SpringBootDemoApplication;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCreateDTO {
    private String name;
    private String author;
}
