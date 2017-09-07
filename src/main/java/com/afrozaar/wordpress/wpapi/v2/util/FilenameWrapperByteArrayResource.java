package com.afrozaar.wordpress.wpapi.v2.util;

import org.springframework.core.io.ByteArrayResource;

public class FilenameWrapperByteArrayResource extends ByteArrayResource {
    final String fileName;

    public FilenameWrapperByteArrayResource(byte[] byteArray, String fileName) {
        super(byteArray);
        this.fileName = fileName;
    }

    public FilenameWrapperByteArrayResource(byte[] byteArray, String description, String fileName) {
        super(byteArray, description);
        this.fileName = fileName;
    }

    @Override
    public String getFilename() {
        return fileName;
    }
}