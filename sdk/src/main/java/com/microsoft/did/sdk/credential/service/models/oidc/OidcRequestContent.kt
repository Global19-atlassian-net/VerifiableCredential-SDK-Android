/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

package com.microsoft.did.sdk.credential.service.models.oidc

import com.microsoft.did.sdk.credential.service.models.attestations.CredentialAttestations
import com.microsoft.did.sdk.util.Constants.CLIENT_ID
import com.microsoft.did.sdk.util.Constants.MAX_AGE
import com.microsoft.did.sdk.util.Constants.REDIRECT_URL
import com.microsoft.did.sdk.util.Constants.RESPONSE_MODE
import com.microsoft.did.sdk.util.Constants.RESPONSE_TYPE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Contents of an OpenID Self-Issued Token Request.
 *
 * @see [OpenID Spec](https://openid.net/specs/openid-connect-core-1_0.html#JWTRequests)
 */
@Serializable
data class OidcRequestContent(

    // what type of object the response should be (should be idtoken).
    @SerialName(RESPONSE_TYPE)
    val responseType: String = "",

    // what mode the response should be sent in (should always be form post).
    @SerialName(RESPONSE_MODE)
    val responseMode: String = "",

    // did of the entity who sent the request.
    @SerialName(CLIENT_ID)
    val clientId: String = "",

    // where the SIOP provider should send response to.
    @SerialName(REDIRECT_URL)
    val redirectUrl: String = "",

    // did of the entity who sent the request.
    val iss: String = "",

    // should contain "openid did_authn"
    val scope: String = "",

    // opaque values that should be passed back to the requester.
    val state: String? = null,
    val nonce: String = "",

    // Claims that are being requested.
    val attestations: CredentialAttestations? = null,

    // Claims that are being requested.
    @SerialName("presentation_definition")
    val credentialPresentationDefinition: CredentialPresentationDefinition? = null,

    // iat, nbf, and exp that need to be checked to see if token has expired
    val exp: Long = 0,
    val iat: Long = 0,
    val nbf: Long = 0,

    // if set to "create", request is just for issuance.
    val prompt: String = "",

    // object for relying parties to give user more details about themselves.
    val registration: Registration? = null,

    // optional parameters
    val aud: String = "",
    @SerialName(MAX_AGE)
    val maxAge: Int = 0
)