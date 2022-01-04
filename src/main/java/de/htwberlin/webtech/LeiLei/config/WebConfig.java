package de.htwberlin.webtech.LeiLei.config;

import de.htwberlin.webtech.LeiLei.utils.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("*")
                .allowedOrigins(
                        "http://localhost:9001/",
                        "https://leilei-frontend.herokuapp.com/"
                );
    }

    /**
     * https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(Constants.STATIC_REZEPT_IMAGES_DIR, registry);
    }

    /**
     *
     * @param dirName
     * @param registry
     */
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path   uploadDir  = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/"+ uploadPath + "/");
    }

}
