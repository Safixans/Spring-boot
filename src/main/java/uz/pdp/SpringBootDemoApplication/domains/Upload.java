package uz.pdp.SpringBootDemoApplication.domains;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Upload {
    private String originalName;
    private String generatedName;
    private long size;
    private String mimeType;
    private String uploadedPath;
}
