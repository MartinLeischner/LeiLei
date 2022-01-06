package de.htwberlin.webtech.LeiLei.service;

import de.htwberlin.webtech.LeiLei.Rezepte.api.Rezept;
import de.htwberlin.webtech.LeiLei.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.persistence.RezeptRepository;
import de.htwberlin.webtech.LeiLei.utils.Constants;
import de.htwberlin.webtech.LeiLei.utils.FileUploadUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RezeptService {

    private final RezeptRepository rezeptRepository;

    public RezeptService(RezeptRepository rezeptRepository) {
        this.rezeptRepository = rezeptRepository;
    }

    public List<Rezept> findAll() {
        List<RezeptEntity> rezepte = rezeptRepository.findAll();
        return rezepte.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());

    }

    public Rezept findById(Long id) {
        var rezeptEntity = rezeptRepository.findById(id);
        return rezeptEntity.map(this::fromEntity).orElse(null);
    }

    public Rezept create(Rezept rezept) {
        var rezeptEntity = fromPojo(rezept);
        rezeptEntity = rezeptRepository.save(rezeptEntity);
        return fromEntity(rezeptEntity);
    }

    /**
     * https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
     *
     * @param rezept
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public Rezept create(Rezept rezept,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        // create unique file name
        var fileExtension = FileUploadUtil.getFileExtension(
                Objects.requireNonNull(multipartFile.getOriginalFilename())
        );
        var formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        var fileName = "rezept_"
                + formatter.format(new Date())
                + "." + fileExtension;
        rezept.setImagePath(fileName);

        String uploadDir = Constants.STATIC_REZEPT_IMAGES_DIR; //+ "rezept_" + savedRezept.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        // TODO catch error during file save

        RezeptEntity rezeptEntity = fromPojo(rezept);
        rezeptEntity = rezeptRepository.save(rezeptEntity);

        return fromEntity(rezeptEntity);
    }

    public Rezept update(Long id, Rezept rezept) {
        var rezeptEntityOptional = rezeptRepository.findById(id);
        if (rezeptEntityOptional.isEmpty()) {
            return null;
        }

        var rezeptEntity = rezeptEntityOptional.get();
        rezeptEntity.setName(rezept.getName());
        rezeptEntity.setIngredient((rezept.getIngredient()));
        rezeptEntity.setDifficulty(rezept.getDifficulty());
        rezeptEntity.setTime(rezept.getTime());
        rezeptEntity = rezeptRepository.save(rezeptEntity);

        return fromEntity(rezeptEntity);
    }

    public boolean deleteById(Long id) {
        if (!rezeptRepository.existsById(id)) {
            return false;
        }

        // TODO remove image file if exists
        rezeptRepository.deleteById(id);
        return true;
    }

    public byte[] getImageForRezeptById(Long id) throws IOException {
        var rezeptEntityOptional = rezeptRepository.findById(id);
        InputStream in = getClass().getResourceAsStream(rezeptEntityOptional.get().getImagePath());
        return IOUtils.toByteArray(in);
    }

    private Rezept fromEntity(RezeptEntity rezeptEntity) {
        var baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/")
                .toUriString();

        return new Rezept(
                rezeptEntity.getId(),
                rezeptEntity.getName(),
                baseUrl + Constants.STATIC_REZEPT_IMAGES_DIR + rezeptEntity.getImagePath(),
                rezeptEntity.getIngredient(),
                rezeptEntity.getDifficulty(),
                rezeptEntity.getTime()
        );
    }

    private RezeptEntity fromPojo(Rezept rezept) {
        return new RezeptEntity(
                rezept.getName(),
                rezept.getImagePath(),
                rezept.getIngredient(),
                rezept.getDifficulty(),
                rezept.getTime()
        );
    }
}
