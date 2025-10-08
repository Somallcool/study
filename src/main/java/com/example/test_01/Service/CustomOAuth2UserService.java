package com.example.test_01.Service;

import com.example.test_01.Entity.MemberEntity;
import com.example.test_01.Repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 카카오 API로부터 원시 사용자 정보를 받아옵니다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 2. 카카오 로그인임을 식별하고, 고유 ID와 속성명을 추출합니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        String kakaoId = oauth2User.getAttribute(userNameAttributeName).toString();

        // 3. 카카오 응답에서 필요한 데이터(이메일, 닉네임)를 추출합니다.
        Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        // 4. 추출한 정보로 DB에 저장/업데이트 로직을 호출합니다.
        MemberEntity member = saveOrUpdate(kakaoId, email, nickname, registrationId);

        // 5. Spring Security에 인증 완료 객체를 반환합니다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE" + member.getRole())), // 권한 설정
                oauth2User.getAttributes(),
                userNameAttributeName
        );
    }

    /**
     * 소셜 로그인 사용자의 DB 저장/업데이트 처리
     */
    private MemberEntity saveOrUpdate(String kakaoId, String email, String nickname, String provider) {

        // 1. 카카오 ID 앞에 접두사를 붙여 유니크 ID를 생성합니다.
        String uniqueId = provider.toUpperCase() + "_" + kakaoId; // 예: KAKAO_1234567

        // 2. DB에서 회원을 찾거나, 없으면 새로운 엔티티를 생성합니다.
        MemberEntity entity = memberRepository.findById(uniqueId)
                .orElse(new MemberEntity());

        // 3. 엔티티에 카카오 정보를 매핑하고 업데이트합니다.
        entity.setId(uniqueId);
        entity.setPw("SOCIAL_LOGIN_DUMMY"); // 소셜 로그인은 비밀번호가 필요 없으므로 더미 값 설정
        entity.setNickname(nickname);
        entity.setProvider(provider.toUpperCase()); // KAKAO
        entity.setRole("USER");
        if (email != null && !email.isEmpty()) {
            entity.setEmail(email);
        }

        // 4. DB에 저장(업데이트)하고 반환합니다.
        return memberRepository.save(entity);
    }
}
