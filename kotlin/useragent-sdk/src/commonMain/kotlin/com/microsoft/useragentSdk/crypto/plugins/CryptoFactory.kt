package com.microsoft.useragentSdk.crypto.plugins

import com.microsoft.useragentSdk.crypto.keyStore.IKeyStore

/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

/**
 * Utility class to handle all CryptoSuite dependency injection
 */
class CryptoFactory {

    /**
     * The key encryptors
     */
    var keyEncrypters: Map<String, CryptoOperations>

    /**
     * The shared key encryptors
     */
    var sharedKeyEncrypters: Map<String, CryptoOperations>

    /**
     * The symmetric content encryptors
     */
    var symmetricEncrypter: Map<String, CryptoOperations>

    /**
     * The message signer
     */
    var messageSigners: Map<String, CryptoOperations>

    /**
     * The hmac operations
     */
    var messageAuthenticationCodeSigners: Map<String, CryptoOperations>

    /**
     * The digest operations
     */
    var messageDigests: Map<String, CryptoOperations>

    /**
     * Key store used by the CryptoFactory
     */
    var keyStore: IKeyStore;

    /**
     * Label for default algorithm
     */
    private val defaultAlgorithm = '*';

    /**
     * Constructs a new CryptoRegistry
     * @param keyStore used to store private jeys
     * @param crypto The suite to use for dependency injection
     */
    constructor (keyStore: IKeyStore, crypto: CryptoOperations) {
        this.keyStore = keyStore;
        this.keyEncrypters = <CryptoSuiteMap>{'*': crypto };
        this.sharedKeyEncrypters = <CryptoSuiteMap>{'*': crypto };
        this.symmetricEncrypter = <CryptoSuiteMap>{'*': crypto };
        this.messageSigners = <CryptoSuiteMap>{'*': crypto };
        this.messageAuthenticationCodeSigners = <CryptoSuiteMap>{'*': crypto };
        this.messageDigests = <CryptoSuiteMap>{'*': crypto };
    }

    /**
     * Gets the key encrypter object given the encryption algorithm's name
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    public getKeyEncrypter (name: string): SubtleCrypto {
        if (this.keyEncrypters[name]) {
            return this.keyEncrypters[name].getKeyEncrypters();
        }
        return this.keyEncrypters[this.defaultAlgorithm].getKeyEncrypters();
    }

    /**
     * Gets the shared key encrypter object given the encryption algorithm's name
     * Used for DH algorithms
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    getSharedKeyEncrypter (name: string): SubtleCrypto {
        if (this.sharedKeyEncrypters[name]) {
            return this.sharedKeyEncrypters[name].getSharedKeyEncrypters();
        }
        return this.sharedKeyEncrypters[this.defaultAlgorithm].getSharedKeyEncrypters();
    }

    /**
     * Gets the SymmetricEncrypter object given the symmetric encryption algorithm's name
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    getSymmetricEncrypter (name: string): SubtleCrypto {
        if (this.symmetricEncrypter[name]) {
            return this.symmetricEncrypter[name].getSymmetricEncrypters();
        }
        return this.symmetricEncrypter[this.defaultAlgorithm].getSymmetricEncrypters();
    }

    /**
     * Gets the message signer object given the signing algorithm's name
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    getMessageSigner (name: string): SubtleCrypto {
        if (this.messageSigners[name]) {
            return this.messageSigners[name].getMessageSigners();
        }
        return this.messageSigners[this.defaultAlgorithm].getMessageSigners();
    }

    /**
     * Gets the mac signer object given the signing algorithm's name
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    getMessageAuthenticationCodeSigners (name: string): SubtleCrypto {
        if (this.messageAuthenticationCodeSigners[name]) {
            return this.messageAuthenticationCodeSigners[name].messageAuthenticationCodeSigners();
        }
        return this.messageAuthenticationCodeSigners[this.defaultAlgorithm].messageAuthenticationCodeSigners();
    }

    /**
     * Gets the message digest object given the digest algorithm's name
     * @param name The name of the algorithm
     * @returns The corresponding crypto API
     */
    getMessageDigest (name: string): SubtleCrypto {
        if (this.messageDigests[name]) {
            return this.messageDigests[name].getMessageDigests();
        }
        return this.messageDigests[this.defaultAlgorithm].getMessageDigests();
    }
}