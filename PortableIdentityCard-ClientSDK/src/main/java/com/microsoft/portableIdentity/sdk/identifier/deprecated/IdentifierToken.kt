package com.microsoft.portableIdentity.sdk.identifier.deprecated

import com.microsoft.portableIdentity.sdk.crypto.CryptoOperations
import com.microsoft.portableIdentity.sdk.identifier.deprecated.document.IdentifierDocument
import com.microsoft.portableIdentity.sdk.registrars.IRegistrar
import com.microsoft.portableIdentity.sdk.resolvers.IResolver
import com.microsoft.portableIdentity.sdk.utilities.ILogger
import com.microsoft.portableIdentity.sdk.utilities.Serializer
import kotlinx.serialization.Serializable

@Serializable
data class IdentifierToken (
    val document: IdentifierDocument,
    val alias: String,
    val signatureKeyReference: String,
    val encryptionKeyReference: String
) {
    companion object {
        private fun tokenize(identifier: Identifier): IdentifierToken {
            return IdentifierToken(
                identifier.document,
                identifier.alias,
                identifier.signatureKeyReference,
                identifier.encryptionKeyReference
            )
        }

        fun serialize(identifier: Identifier): String {
            val token =
                tokenize(identifier)
            return Serializer.stringify(IdentifierToken.serializer(), token)
        }

        fun deserialize(
            identifierToken: String,
            cryptoOperations: CryptoOperations,
            logger: ILogger,
            resolver: IResolver,
            registrar: IRegistrar
        ): Identifier {
            val token = Serializer.parse(IdentifierToken.serializer(), identifierToken)
            return Identifier(
                alias = token.alias,
                document = token.document,
                signatureKeyReference = token.signatureKeyReference,
                encryptionKeyReference = token.encryptionKeyReference,
                cryptoOperations = cryptoOperations,
                logger = logger,
                resolver = resolver,
                registrar = registrar
            )
        }
    }
}