package ro.fmiunibuv.tema2cripto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserData {
    private int bitSize;
    private Integer fileSize;
}
