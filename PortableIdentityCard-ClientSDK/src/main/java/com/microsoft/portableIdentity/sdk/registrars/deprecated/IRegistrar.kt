// Copyright (c) Microsoft Corporation. All rights reserved

package com.microsoft.portableIdentity.sdk.registrars.deprecated

import com.microsoft.portableIdentity.sdk.crypto.CryptoOperations
import com.microsoft.portableIdentity.sdk.identifier.deprecated.document.IdentifierDocument

/**
 * @interface defining methods and properties
 * to be implemented by specific registration methods.
 */
abstract class IRegistrar() {

    /**
     * Registers the identifier document on the ledger
     * returning the identifier generated by the registrar.
     * @param identifierDocument to be registered.
     * @param signingKeyReference reference to the key to be used for signing request.
     * @return IdentifierDocument that was registered.
     * @throws Error if unable to register Identifier Document.
     */
    abstract suspend fun register(document: RegistrationDocument, signatureKeyRef: String, crypto: CryptoOperations): IdentifierDocument
}