package com.mobileleader.edoc.monitoring.config.storage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import com.mobileleader.edoc.monitoring.common.type.FileStorageType;
import com.mobileleader.webform.common.storage.Ddims7Storage;
import com.mobileleader.webform.common.storage.FileStorage;
import com.mobileleader.webform.common.storage.LocalStorage;
import com.mobileleader.webform.common.storage.cache.FileCacheManager;
import com.mobileleader.webform.common.storage.cache.FileCacheMode;
import com.mobileleader.webform.sform.streaming.cache.SFromCacheManager;
import lombok.RequiredArgsConstructor;

@Configuration
@PropertySources(value = {
    @PropertySource("classpath:properties/storage.properties"),
    @PropertySource("classpath:properties/service.properties")
})
@RequiredArgsConstructor
public class FileStorageConfiguration {

    private final Environment env;
    
    @Bean
    public FileStorage fileStorage() {
        
        FileStorage fileStorage = null;
        String fileStorageType = env.getProperty("storage.type");
        
        // default is LocalStorage (avoid returning null)
        if(FileStorageType.LOCAL.getCode().equalsIgnoreCase(fileStorageType) || StringUtils.isEmpty(fileStorageType)) {
            fileStorage = getLocalStorage();
        }
        
        if(FileStorageType.ECM.getCode().equalsIgnoreCase(fileStorageType)) {
            fileStorage = getEcmStorage();
        }
        
        if(FileStorageType.ETC.getCode().equalsIgnoreCase(env.getProperty("storage.type"))) {
            fileStorage = new EdocFileStorage(env);
        }
        
        return fileStorage;
    }
    
    private LocalStorage getLocalStorage() {
        return new LocalStorage(env.getProperty("rootPath"));
    }
    
    private Ddims7Storage getEcmStorage() {
        Ddims7Storage fileStorage = new Ddims7Storage();
        fileStorage.setServerIp(env.getProperty("server.ip"));
        fileStorage.setServerPort(env.getProperty("server.port", Integer.class));
        fileStorage.setUserId(env.getProperty("user.id"));
        fileStorage.setUserPw(env.getProperty("user.password"));
        return fileStorage;
    }
    
    @Bean
    public FileStorage fileCacheStorage() {
        FileCacheManager fileCacheStorage = new FileCacheManager();
        fileCacheStorage.setLowStorage(fileStorage());
        fileCacheStorage.setFileCacheRoot(env.getProperty("cache.fileCacheRoot"));
        fileCacheStorage.setFileCacheMode(env.getProperty("cache.fileCacheMode", FileCacheMode.class));
        fileCacheStorage.setInfoMapSize(env.getProperty("fileCache.info.size", Integer.class));
        fileCacheStorage.setInfoMapTimeout(env.getProperty("fileCache.info.timeout", Integer.class));
        fileCacheStorage.setFileMapSize(env.getProperty("fileCache.file.size", Integer.class));
        fileCacheStorage.setFileMapTimeout(env.getProperty("fileCache.file.timeout", Integer.class));
        fileCacheStorage.setBinaryMapSize(env.getProperty("fileCache.binary.size", Integer.class));
        fileCacheStorage.setBinaryMapTimeout(env.getProperty("fileCache.binary.timeout", Integer.class));
        return fileCacheStorage;
    }
    
    @Bean
    public SFromCacheManager sFormCacheManager() {
        SFromCacheManager sFormCacheManager = new SFromCacheManager();
        sFormCacheManager.setFileStorage(fileCacheStorage());
        sFormCacheManager.setFileInfoMapSize(env.getProperty("sformCache.fileInfo.size", Integer.class));
        sFormCacheManager.setFileInfoMapTimeout(env.getProperty("sformCache.fileInfo.timeout", Integer.class));
        sFormCacheManager.setPageInfoMapSize(env.getProperty("sformCache.pageInfo.size", Integer.class));
        sFormCacheManager.setPageInfoMapTimeout(env.getProperty("sformCache.pageInfo.timeout", Integer.class));
        sFormCacheManager.setStreamingMapSize(env.getProperty("sformCache.streaming.size", Integer.class));
        sFormCacheManager.setStremingMapTimeout(env.getProperty("sformCache.streaming.timeout", Integer.class));
        sFormCacheManager.setThumbnailMapSize(env.getProperty("sformCache.thumbanil.size", Integer.class));
        sFormCacheManager.setThumbnailMapTimeout(env.getProperty("sformCache.thumbanil.timeout", Integer.class));
        sFormCacheManager.setAnnotationInfoMapSize(env.getProperty("sformCache.annotationInfo.size", Integer.class));
        sFormCacheManager.setAnnotationInfoMapTimeout(env.getProperty("sformCache.annotationInfo.timeout", Integer.class));
        return sFormCacheManager;
    }
}
