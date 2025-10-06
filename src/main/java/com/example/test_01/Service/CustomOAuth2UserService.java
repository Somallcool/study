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
        // 1. 기본 OAuth2UserService를 생성하여 사용자 정보를 가져옵니다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 2. 서비스 등록 ID (여기서는 'kakao')
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 3. 사용자 정보의 키 (application.properties에서 설정한 'id')
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 카카오 사용자 정보 추출
        String kakaoId = oauth2User.getAttribute(userNameAttributeName).toString();

        // 카카오 계정 정보는 'kakao_account' 아래에 있습니다.
        Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        // 5. DB 저장 또는 업데이트 로직 실행
        MemberEntity member = saveOrUpdate(kakaoId, email, nickname);

        // 6. SecurityContext에 저장할 객체 생성 및 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole())), // 권한 설정
                oauth2User.getAttributes(), // 사용자 정보 속성
                userNameAttributeName // 사용자 고유 ID 속성 키 (여기서는 'id')
        );
    }

    /**
     * 소셜 로그인 사용자의 DB 저장/업데이트 처리
     */
    private MemberEntity saveOrUpdate(String kakaoId, String email, String nickname) {
        // 소셜 로그인 사용자의 ID는 일반 회원과 충돌을 막기 위해 접두사를 붙여 저장
        String uniqueId = "KAKAO_" + kakaoId;

        // 1. 기존 회원이 있는지 찾습니다.
        MemberEntity entity = memberRepository.findById(uniqueId)
                .orElse(new MemberEntity()); // 없으면 새 객체 생성

        // 2. 정보 업데이트/초기 설정
        entity.setId(uniqueId);
        entity.setPw("SOCIAL_LOGIN_DUMMY"); // Spring Security에서 일반 로그인과 분리하기 위한 더미 값
        entity.setNickname(nickname);
        entity.setRole("USER");

        // 이메일은 카카오에서 제공하지만 동의가 필수 사항이 아니므로 null 체크 필요
        if (email != null && !email.isEmpty()) {
            // entity.setEmail(email); // MemberEntity에 email 필드가 있다면 저장
        }

        return memberRepository.save(entity);
    }
}
