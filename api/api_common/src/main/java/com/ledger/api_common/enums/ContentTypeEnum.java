package com.ledger.api_common.enums;

public enum ContentTypeEnum {
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif"),
    AUDIO_MP3("audio/mpeg"),
    VIDEO_MP4("video/mp4"),
    APPLICATION_JSON("application/json"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_EXCEL("application/vnd.ms-excel"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_JAVASCRIPT("application/javascript");

    private final String contentType;

    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
