// Copyright (c) Microsoft Corporation. All rights reserved

package com.microsoft.portableIdentity.sdk.registrars.deprecated

import com.microsoft.portableIdentity.sdk.crypto.CryptoOperations
import com.microsoft.portableIdentity.sdk.identifier.deprecated.document.IdentifierDocument
import com.microsoft.portableIdentity.sdk.utilities.SdkLog

class NullRegistrar(): IRegistrar() {
    override suspend fun register(document: RegistrationDocument, signatureKeyRef: String, crypto: CryptoOperations): IdentifierDocument {
        throw SdkLog.error("Attempted to register from the null registrar.")
    }
}