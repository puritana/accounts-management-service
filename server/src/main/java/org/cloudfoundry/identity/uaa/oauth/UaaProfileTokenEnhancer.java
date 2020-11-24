package org.cloudfoundry.identity.uaa.oauth;

import org.cloudfoundry.identity.uaa.authentication.UaaAuthentication;
import org.cloudfoundry.identity.uaa.authentication.UaaPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.cloudfoundry.identity.uaa.oauth.token.ClaimConstants.PROFILE;
import static org.cloudfoundry.identity.uaa.oauth.token.ClaimConstants.SUB;

public class UaaProfileTokenEnhancer implements UaaTokenEnhancer {

    @Override
    public Map<String, String> getExternalAttributes(OAuth2Authentication authentication) {
        Map<String, String> externalAttributes = new HashMap<>();

        externalAttributes.put(SUB, ((UaaPrincipal)authentication.getPrincipal()).getId());

        return externalAttributes;
    }

    @Override
    public Map<String, Object> enhance(Map<String, Object> claims, OAuth2Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UaaPrincipal)) {
            return Collections.emptyMap();
        }

        Map<String, Object> profile = new HashMap<>();

        profile.put(PROFILE, getExternalAttributes(authentication));

        System.out.println("Returning " + profile);

        return profile;
    }
}
