package nextstep.infra.github.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GithubProfileResponse {
    private String accessToken;
    private String email;

}