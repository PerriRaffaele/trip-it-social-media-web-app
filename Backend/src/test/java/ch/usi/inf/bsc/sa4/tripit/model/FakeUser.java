package ch.usi.inf.bsc.sa4.tripit.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class FakeUser implements OAuth2User {

  private HashMap<String, Object> attributesMap = new HashMap<>();
  private OAuth2AuthenticationToken token;

  public FakeUser(String sub, String name, String nickname, String picture) {
    this.attributesMap.put("sub", sub);
    this.attributesMap.put("nickname", nickname);
    this.attributesMap.put("name", name);
    this.attributesMap.put("picture", picture);
    this.token = new OAuth2AuthenticationToken(this, Collections.emptyList(), name);
  }

  @Override
  public <A> A getAttribute(String name) {
    return (A) this.attributesMap.get(name);
  }

  @Override
  public Map<String, Object> getAttributes() {
    return Collections.unmodifiableMap(this.getAttributes());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  public OAuth2AuthenticationToken getToken() {
    return this.token;
  }
}
