package com.microsoft.useragentSdk.crypto.keyStore
/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License. See License in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

/**
 * Interface defining methods and properties to
 * be implemented by specific key stores.
 */
interface IKeyStore {
    /**
     * Returns the key associated with the specified
     * key reference.
     * @param keyIdentifier for which to return the key.
     * @param [publicKeyOnly] True if only the public key is needed.
     */
    fun getSecretKey(keyReference: String, publicKeyOnly: Boolean?): SecretKey

    fun getPrivateKey(keyReference: String, publicKeyOnly: Boolean?): PrivateKey

    fun getPublicKey(keyReference: String, publicKeyOnly: Boolean?): PublicKey

    /**
     * Saves the specified key to the key store using
     * the key reference.
     * @param keyReference Reference for the key being saved.
     * @param key being saved to the key store.
     */
    fun save(keyReference: String, key: SecretKey): Unit
    fun save(keyReference: String, key: PrivateKey): Unit
    fun save(keyReference: String, key: PublicKey): Unit

    /**
     * Lists all key references with their corresponding key ids
     */
    fun list(): Map<String, String>
}