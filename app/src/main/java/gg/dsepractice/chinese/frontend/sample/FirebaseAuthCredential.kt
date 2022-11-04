package gg.dsepractice.chinese.frontend.sample

import io.grpc.CallCredentials
import java.util.concurrent.Executor
import io.grpc.Metadata
import io.grpc.Status

class FirebaseAuthCredential(private val idToken: String) : CallCredentials() {
    override fun applyRequestMetadata(
        requestInfo: RequestInfo?,
        appExecutor: Executor?,
        applier: MetadataApplier?
    ) {
        appExecutor?.execute {
            try {
                val headers = Metadata()
                headers.put(authorizationMetadataKey, "$bearerType $idToken")
                applier?.apply(headers)
            } catch (e: Throwable) {
                applier?.fail(Status.UNAUTHENTICATED.withCause(e))
            }
        }
    }

    override fun thisUsesUnstableApi() {

    }

}