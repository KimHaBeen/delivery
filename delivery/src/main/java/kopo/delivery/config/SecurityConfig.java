package kopo.delivery.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;

public class SecurityConfig {

    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }
}
