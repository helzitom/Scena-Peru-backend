package com.escenaperu.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MVP de un solo proceso. Si escalas a varias instancias detras de un load
 * balancer, cada instancia lleva su propio contador -> mover esto a un
 * limitador compartido (Bucket4j + Redis) para que el limite sea global.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Set<String> RUTAS_LIMITADAS = Set.of("/api/auth/login", "/api/usuarios/registro");
    private static final int MAX_INTENTOS = 5;
    private static final long VENTANA_SEGUNDOS = 60;

    private final ConcurrentHashMap<String, List<Instant>> intentosPorClave = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (RUTAS_LIMITADAS.contains(request.getRequestURI())) {
            String clave = clienteId(request) + ":" + request.getRequestURI();
            Instant ahora = Instant.now();
            List<Instant> intentos = intentosPorClave.computeIfAbsent(clave, k -> new ArrayList<>());

            synchronized (intentos) {
                intentos.removeIf(t -> t.isBefore(ahora.minusSeconds(VENTANA_SEGUNDOS)));
                if (intentos.size() >= MAX_INTENTOS) {
                    response.setStatus(429);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Demasiados intentos, espera un minuto\"}");
                    return;
                }
                intentos.add(ahora);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String clienteId(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        return xff != null ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }
}