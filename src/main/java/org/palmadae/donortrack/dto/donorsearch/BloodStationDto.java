package org.palmadae.donortrack.dto.donorsearch;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BloodStationDto {

    private Long id;
    private String title;
    private String address;
    private Double lat;
    private Double lng;
    private String site;

    @JsonProperty("phone_numbers")
    private List<Map<String, Object>> phoneNumbers;

    public String getFirstPhone() {
        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            Object phone = phoneNumbers.get(0).get("phone");
            return phone != null ? phone.toString() : "Телефон не указан";
        }
        return "Телефон не указан";
    }

    @JsonProperty("without_registration")
    private Boolean withoutRegistration;

    public String getRegistrationText() {
        return Boolean.TRUE.equals(withoutRegistration)
                ? "Без предварительной записи"
                : "Требуется запись";
    }

    private String o_plus;
    private String o_minus;
    private String a_plus;
    private String a_minus;
    private String b_plus;
    private String b_minus;
    private String ab_plus;
    private String ab_minus;

    @JsonProperty("blood_group")
    private List<String> bloodGroup;

    private Boolean closed;
}