package example.micronaut;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller // <1>
public class HomeController {

    @Secured(SecurityRule.IS_ANONYMOUS) // <2>
    @Get // <4>
    @Produces(MediaType.TEXT_HTML)
    public String index(@Nullable Authentication authentication) {
        if (authentication == null) {
            return """
                    <!DOCTYPE html>
                    <body>
                    <h2>username: Anonymous</h2>
                    <nav>
                        <ul>
                            <li><a href="/oauth/login/google">Enter</a></li>
                        </ul>
                    </nav>
                    </body>
                    </html>
                """;
        }
        return """
                    <!DOCTYPE html>
                    <body>
                    <h2>username: %s</h2>
                    <nav>
                        <ul>
                            <li><a href="/logout">Logout</a></li>
                            <li><a href="/test">Get details</a></li>
                        </ul>
                    </nav>
                    </body>
                    </html>
                """.formatted(authentication.getAttributes().get("email"));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/test")
    public Object get(Authentication authentication) {
        return authentication;
    }
}
