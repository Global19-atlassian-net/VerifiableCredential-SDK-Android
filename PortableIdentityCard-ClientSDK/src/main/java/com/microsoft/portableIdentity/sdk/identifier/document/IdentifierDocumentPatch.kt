package com.microsoft.portableIdentity.sdk.identifier.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class IdentifierDocumentPatch (
    val action: String,
/*    @SerialName("publicKeys")
    val publicKeys: List<IdentifierDocumentPublicKey>,*/
    @SerialName("document")
    val document: IdentifierDocumentPayload
){}