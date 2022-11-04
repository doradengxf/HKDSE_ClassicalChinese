package gg.dsepractice.chinese.frontend.sample

import io.grpc.Metadata

val authorizationMetadataKey: Metadata.Key<String> =
    Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
const val bearerType = "Bearer"