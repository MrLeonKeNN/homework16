package epam.ilyankov.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {

    private String name;
    private Double cell;

}
