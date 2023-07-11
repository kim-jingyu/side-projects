package shoppingmall.server.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemImgDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    public ItemImgDto storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uploadFileName = multipartFile.getOriginalFilename();
        String ext = getExt(uploadFileName);
        String storeFileName = getStoreFileName(ext);
        String storedPath = getPath(storeFileName);

        multipartFile.transferTo(new File(storedPath));

        return new ItemImgDto(null, uploadFileName, storeFileName, storedPath, null);
    }

    public void deleteFile(String filePath) throws IOException {
        Files.delete(Path.of(filePath));
    }

    private String getPath(String storeFileName) {
        return itemImgLocation + storeFileName;
    }

    private String getStoreFileName(String ext) {
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + ext;
        return storeFileName;
    }

    private String getExt(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf(".");
        String ext = uploadFileName.substring(pos + 1);
        return ext;
    }
}
