package dev.garage474.mspagamento.adapter.dto.pagseguro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagSeguroAddressDTO {

    private String street;
    private String number;
    private String complement;
    private String locality;
    private String city;

    @JsonProperty("region_code")
    private String regionCode;
    private String country;

    @JsonProperty("postal_code")
    private String postalCode;


}
