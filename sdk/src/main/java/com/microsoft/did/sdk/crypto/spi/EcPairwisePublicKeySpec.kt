// Copyright (c) Microsoft Corporation. All rights reserved

package com.microsoft.did.sdk.crypto.spi

import org.spongycastle.jce.interfaces.ECPrivateKey
import java.security.spec.KeySpec

class EcPairwisePublicKeySpec(
    val privateKey: ECPrivateKey
) : KeySpec