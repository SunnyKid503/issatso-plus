package com.university.forum.usermanagement.Shared.Utils;

import java.util.Set;
import java.util.UUID;

/***
 * A dumb class for those who are lazy enough to escape the login process
 * and generate an access token to be displayed in the terminal for front-end reasons.
 */
public class JwtGenerator extends JwtTokenUtil{
    protected JwtGenerator()
    {
        super.accessTokenExpiration = 3009600000L;
        super.secretKey = "+nyslZ373c5IbdSzwC7jPDE72VKm2saNxu2cAqI1d4fjTeWReW8BCuBKyLylDGkI";
        super.refreshTokenExpiration = 3009600000L;
    }

    public static void main(String[] args) {
        System.out.println(new JwtGenerator().generateAccessToken(
          UUID.fromString("3aa5e578-9e13-4c08-ab1d-1537a9cb47a7"),
          "emily.williams@email.com",
          Set.of("PROFESSOR_ROLE")
        ));
    }
}
