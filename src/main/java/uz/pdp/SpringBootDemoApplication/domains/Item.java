package uz.pdp.SpringBootDemoApplication.domains;


import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Component
public class Item {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String path="/Users/safixonabdusattorov/Documents/Upload/"; // file upload qilingan url shu yerda bo'lishi kerak

}
