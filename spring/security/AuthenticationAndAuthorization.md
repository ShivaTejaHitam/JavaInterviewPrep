Below is a **production-grade JWT authentication & authorization design** for **Spring Boot (Spring Security 6 / Boot 3+)**. This is not just a demo-level setup—it includes **refresh tokens, roles, secure password storage, filters, exception handling, and best practices** used in real deployments.

---

# 1. Architecture Overview (Production-Ready)

Typical JWT-based authentication flow:

```
Client → Login (username/password)
        ↓
Spring Security AuthenticationManager
        ↓
Generate JWT Access Token (+ Refresh Token)
        ↓
Client sends JWT in Authorization header
        ↓
JWT Filter validates token
        ↓
Spring Security grants access based on roles
```

Production-grade setup includes:

* Access Token (short-lived)
* Refresh Token (long-lived, stored in DB)
* Role-based authorization
* Secure password hashing (BCrypt)
* Stateless authentication
* Token revocation support
* Exception handling
* CORS configuration
* Secure key management

---

# 2. Dependencies (Spring Boot 3+)

**Maven**

```xml
<dependencies>

    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.5</version>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

</dependencies>
```

---

# 3. User Entity (With Roles)

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
```

---

# Role Enum

```java
public enum Role {

    USER,
    ADMIN

}
```

---

# 4. Refresh Token Entity (Important for Production)

```java
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private Instant expiryDate;

    @ManyToOne
    private User user;

}
```

Why this matters:

* Enables logout
* Enables token revocation
* Supports session management

---

# 5. JWT Utility Class

Handles:

* Token generation
* Validation
* Claim extraction

```java
@Service
public class JwtService {

    private static final String SECRET =
            "very-strong-production-secret-key";

    private Key getSignKey() {
        byte[] keyBytes =
                Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user) {

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                            System.currentTimeMillis()
                            + 1000 * 60 * 15
                        ))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
```

Production advice:

* Store secret in **environment variables**
* Never hardcode secrets

---

# 6. UserDetailsService Implementation

```java
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(
            UserRepository repo) {

        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username) {

        User user = repo
                .findByUsername(username)
                .orElseThrow();

        return new org.springframework.security
                .core.userdetails.User(

                user.getUsername(),
                user.getPassword(),

                List.of(
                    new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()
                    )
                )
        );
    }

}
```

---

# 7. JWT Authentication Filter

Core component.

Runs on every request.

```java
@Component
public class JwtAuthFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String header =
                request.getHeader("Authorization");

        if (header == null ||
            !header.startsWith("Bearer ")) {

            filterChain.doFilter(
                    request, response);
            return;
        }

        String token =
                header.substring(7);

        String username =
                jwtService.extractUsername(token);

        if (username != null &&
            SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails user =
                    userService
                        .loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

}
```

---

# 8. Security Configuration (Spring Security 6)

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                sm -> sm.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/auth/**")
                .permitAll()
                .requestMatchers(
                        "/api/admin/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
            )
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
```

---

# 9. Authentication Controller

Login endpoint.

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest req) {

        Authentication auth =
                manager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                ));

        UserDetails user =
                (UserDetails) auth.getPrincipal();

        String token =
                jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(token));
    }

}
```

---

# 10. Password Encoding (Required)

```java
@Bean
public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
}
```

Never store raw passwords.

Always use:

* BCrypt
* Argon2 (even better)

---

# 11. Role-Based Authorization

Controller example:

```java
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String adminOnly() {

        return "Admin content";
    }

}
```

---

# 12. Refresh Token Flow (Production Must-Have)

Typical flow:

```
Login → Access Token + Refresh Token
Access expires → Use Refresh Token
Generate new Access Token
```

Never rely on long-lived access tokens.

---

# 13. Exception Handling (Important)

Handle:

* Expired token
* Invalid token
* Unauthorized access

```java
@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized");
    }

}
```

---

# 14. Production Security Best Practices

These separate demo code from real systems.

---

## Use Strong Secret Keys

Use:

```
256-bit Base64 secret
```

Store in:

```
ENV variable
Vault
AWS Secrets Manager
```

Never:

```
Hardcode secrets
```

---

## Use HTTPS Only

JWT must never travel over HTTP.

Always:

```
HTTPS
TLS 1.2+
```

---

## Access Token Lifetime

Recommended:

```
Access Token: 10–15 minutes
Refresh Token: 7–30 days
```

---

## Token Blacklisting

Required for:

```
Logout
Password change
Account disable
```

Use:

```
Redis (recommended)
```

---

## Enable CORS Properly

```java
@Bean
public CorsConfigurationSource corsConfig() {

    CorsConfiguration config =
            new CorsConfiguration();

    config.setAllowedOrigins(
            List.of("https://yourdomain.com"));

    config.setAllowedMethods(
            List.of("*"));

    config.setAllowedHeaders(
            List.of("*"));

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
            "/**",
            config);

    return source;
}
```

---

# 15. Production Folder Structure

Recommended:

```
auth/
 ├── controller/
 ├── service/
 ├── entity/
 ├── repository/
 ├── security/
 │    ├── JwtService
 │    ├── JwtFilter
 │    ├── SecurityConfig
 │    ├── JwtAuthEntryPoint
 ├── dto/
 ├── exception/
```

---

# 16. Optional Enterprise Enhancements

For serious production workloads:

* Redis token store
* OAuth2 integration
* Multi-device login support
* Device fingerprinting
* Rate limiting
* Audit logging
* MFA (2FA)
* Key rotation

---

# 17. Common Production Mistakes

These cause real-world failures.

Avoid:

* Long-lived JWT tokens
* No refresh tokens
* Hardcoded secrets
* No token revocation
* Not validating signature
* Not validating expiration
* Missing HTTPS

---

# 18. If You Want Real Enterprise-Level Setup

I can generate:

* Full **production-grade project**
* Using **Spring Boot 3**
* With **JWT + Refresh Tokens**
* With **Redis**
* With **Role-based Access**
* With **Docker support**
* With **PostgreSQL**
* With **Flyway migrations**

Just tell:

1. Spring Boot version
2. Database (PostgreSQL/MySQL/etc.)
3. Whether Redis is available
4. Whether you want OAuth2 later

And I can build a **real deployable skeleton**, not just snippets.
