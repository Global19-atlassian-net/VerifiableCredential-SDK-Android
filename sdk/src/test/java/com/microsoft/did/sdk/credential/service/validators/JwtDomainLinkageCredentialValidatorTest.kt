// Copyright (c) Microsoft Corporation. All rights reserved

package com.microsoft.did.sdk.credential.service.validators

import com.microsoft.did.sdk.credential.service.models.serviceResponses.DnsBindingResponse
import com.microsoft.did.sdk.crypto.protocols.jose.jws.JwsToken
import com.microsoft.did.sdk.util.serializer.Serializer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Test

class JwtDomainLinkageCredentialValidatorTest {

    private val serializer = Serializer()
    private val jwtDomainLinkageCredentialValidator: JwtDomainLinkageCredentialValidator
    private val mockedJwtValidator: JwtValidator = mockk()

    init {
        jwtDomainLinkageCredentialValidator = JwtDomainLinkageCredentialValidator(mockedJwtValidator, serializer)
        mockkObject(JwsToken)
    }

    @Test
    fun `validateConfigDoc`() {
        val docJwt =
            """{
    "@context": "https://identity.foundation/.well-known/contexts/did-configuration-v0.0.jsonld",
    "linked_dids": [
        "eyJhbGciOiJFUzI1NksiLCJraWQiOiJkaWQ6aW9uOkVpQjdKLWswZjNJbUctenlGVUZSbVY0Z2FPc2Rmankza2RkSl9kOU9PdjdzaEE_LWlvbi1pbml0aWFsLXN0YXRlPWV5SmtaV3gwWVY5b1lYTm9Jam9pUldsQ1psQlVXakZZYlRSNFRtTTVOblpRY25kdldUQTFia3hmTkRWWlVHMTViMVZuYjNsU2FrRkxRVTFrWnlJc0luSmxZMjkyWlhKNVgyTnZiVzFwZEcxbGJuUWlPaUpGYVVGRlRrMDBjRTVMWWtaNlUyWXhSazFZUkU1c1lUQlZObEZDVkcxclkxQkhPRTF3VEdOUlpGWlBUa3RuSW4wLmV5SjFjR1JoZEdWZlkyOXRiV2wwYldWdWRDSTZJa1ZwUWkxRFRUVTFTRjkyTjB4YVFXOUNXRWsyT1haRWNuQTFYMmQwWDJwdFF6SnVibGhDTFZVNVRsUnhWMmNpTENKd1lYUmphR1Z6SWpwYmV5SmhZM1JwYjI0aU9pSnlaWEJzWVdObElpd2laRzlqZFcxbGJuUWlPbnNpY0hWaWJHbGpYMnRsZVhNaU9sdDdJbWxrSWpvaWMybG5Yek0zTXpkbU1qSTJJaXdpZEhsd1pTSTZJa1ZqWkhOaFUyVmpjREkxTm1zeFZtVnlhV1pwWTJGMGFXOXVTMlY1TWpBeE9TSXNJbXAzYXlJNmV5SnJkSGtpT2lKRlF5SXNJbU55ZGlJNkluTmxZM0F5TlRack1TSXNJbmdpT2lKc2NrNVBZMTh5YjNSak0zWlVlRWx1WlRCbGNraFVVeTFHYkdGNE9VSnJRMkZXZWtKTFZGVmZaMkpuSWl3aWVTSTZJblZVTkZkNGJubFJZVnAzTFRSNlRsaEJkVE0wZGxscVNuWjNaRmh1WkRKNFdqZHhWbVZoYW1kMGMwVWlmU3dpY0hWeWNHOXpaU0k2V3lKaGRYUm9JaXdpWjJWdVpYSmhiQ0pkZlYxOWZWMTkjc2lnXzM3MzdmMjI2In0.eyJzdWIiOiJkaWQ6aW9uOkVpQjdKLWswZjNJbUctenlGVUZSbVY0Z2FPc2Rmankza2RkSl9kOU9PdjdzaEE_LWlvbi1pbml0aWFsLXN0YXRlPWV5SmtaV3gwWVY5b1lYTm9Jam9pUldsQ1psQlVXakZZYlRSNFRtTTVOblpRY25kdldUQTFia3hmTkRWWlVHMTViMVZuYjNsU2FrRkxRVTFrWnlJc0luSmxZMjkyWlhKNVgyTnZiVzFwZEcxbGJuUWlPaUpGYVVGRlRrMDBjRTVMWWtaNlUyWXhSazFZUkU1c1lUQlZObEZDVkcxclkxQkhPRTF3VEdOUlpGWlBUa3RuSW4wLmV5SjFjR1JoZEdWZlkyOXRiV2wwYldWdWRDSTZJa1ZwUWkxRFRUVTFTRjkyTjB4YVFXOUNXRWsyT1haRWNuQTFYMmQwWDJwdFF6SnVibGhDTFZVNVRsUnhWMmNpTENKd1lYUmphR1Z6SWpwYmV5SmhZM1JwYjI0aU9pSnlaWEJzWVdObElpd2laRzlqZFcxbGJuUWlPbnNpY0hWaWJHbGpYMnRsZVhNaU9sdDdJbWxrSWpvaWMybG5Yek0zTXpkbU1qSTJJaXdpZEhsd1pTSTZJa1ZqWkhOaFUyVmpjREkxTm1zeFZtVnlhV1pwWTJGMGFXOXVTMlY1TWpBeE9TSXNJbXAzYXlJNmV5SnJkSGtpT2lKRlF5SXNJbU55ZGlJNkluTmxZM0F5TlRack1TSXNJbmdpT2lKc2NrNVBZMTh5YjNSak0zWlVlRWx1WlRCbGNraFVVeTFHYkdGNE9VSnJRMkZXZWtKTFZGVmZaMkpuSWl3aWVTSTZJblZVTkZkNGJubFJZVnAzTFRSNlRsaEJkVE0wZGxscVNuWjNaRmh1WkRKNFdqZHhWbVZoYW1kMGMwVWlmU3dpY0hWeWNHOXpaU0k2V3lKaGRYUm9JaXdpWjJWdVpYSmhiQ0pkZlYxOWZWMTkiLCJpc3MiOiJkaWQ6aW9uOkVpQjdKLWswZjNJbUctenlGVUZSbVY0Z2FPc2Rmankza2RkSl9kOU9PdjdzaEE_LWlvbi1pbml0aWFsLXN0YXRlPWV5SmtaV3gwWVY5b1lYTm9Jam9pUldsQ1psQlVXakZZYlRSNFRtTTVOblpRY25kdldUQTFia3hmTkRWWlVHMTViMVZuYjNsU2FrRkxRVTFrWnlJc0luSmxZMjkyWlhKNVgyTnZiVzFwZEcxbGJuUWlPaUpGYVVGRlRrMDBjRTVMWWtaNlUyWXhSazFZUkU1c1lUQlZObEZDVkcxclkxQkhPRTF3VEdOUlpGWlBUa3RuSW4wLmV5SjFjR1JoZEdWZlkyOXRiV2wwYldWdWRDSTZJa1ZwUWkxRFRUVTFTRjkyTjB4YVFXOUNXRWsyT1haRWNuQTFYMmQwWDJwdFF6SnVibGhDTFZVNVRsUnhWMmNpTENKd1lYUmphR1Z6SWpwYmV5SmhZM1JwYjI0aU9pSnlaWEJzWVdObElpd2laRzlqZFcxbGJuUWlPbnNpY0hWaWJHbGpYMnRsZVhNaU9sdDdJbWxrSWpvaWMybG5Yek0zTXpkbU1qSTJJaXdpZEhsd1pTSTZJa1ZqWkhOaFUyVmpjREkxTm1zeFZtVnlhV1pwWTJGMGFXOXVTMlY1TWpBeE9TSXNJbXAzYXlJNmV5SnJkSGtpT2lKRlF5SXNJbU55ZGlJNkluTmxZM0F5TlRack1TSXNJbmdpT2lKc2NrNVBZMTh5YjNSak0zWlVlRWx1WlRCbGNraFVVeTFHYkdGNE9VSnJRMkZXZWtKTFZGVmZaMkpuSWl3aWVTSTZJblZVTkZkNGJubFJZVnAzTFRSNlRsaEJkVE0wZGxscVNuWjNaRmh1WkRKNFdqZHhWbVZoYW1kMGMwVWlmU3dpY0hWeWNHOXpaU0k2V3lKaGRYUm9JaXdpWjJWdVpYSmhiQ0pkZlYxOWZWMTkiLCJuYmYiOjE2MDI3MDUwMTYsInZjIjp7IkBjb250ZXh0IjpbImh0dHBzOi8vd3d3LnczLm9yZy8yMDE4L2NyZWRlbnRpYWxzL3YxIiwiaHR0cHM6Ly9pZGVudGl0eS5mb3VuZGF0aW9uLy53ZWxsLWtub3duL2NvbnRleHRzL2RpZC1jb25maWd1cmF0aW9uLXYwLjAuanNvbmxkIl0sImlzc3VlciI6ImRpZDppb246RWlCN0otazBmM0ltRy16eUZVRlJtVjRnYU9zZGZqeTNrZGRKX2Q5T092N3NoQT8taW9uLWluaXRpYWwtc3RhdGU9ZXlKa1pXeDBZVjlvWVhOb0lqb2lSV2xDWmxCVVdqRlliVFI0VG1NNU5uWlFjbmR2V1RBMWJreGZORFZaVUcxNWIxVm5iM2xTYWtGTFFVMWtaeUlzSW5KbFkyOTJaWEo1WDJOdmJXMXBkRzFsYm5RaU9pSkZhVUZGVGswMGNFNUxZa1o2VTJZeFJrMVlSRTVzWVRCVk5sRkNWRzFyWTFCSE9FMXdUR05SWkZaUFRrdG5JbjAuZXlKMWNHUmhkR1ZmWTI5dGJXbDBiV1Z1ZENJNklrVnBRaTFEVFRVMVNGOTJOMHhhUVc5Q1dFazJPWFpFY25BMVgyZDBYMnB0UXpKdWJsaENMVlU1VGxSeFYyY2lMQ0p3WVhSamFHVnpJanBiZXlKaFkzUnBiMjRpT2lKeVpYQnNZV05sSWl3aVpHOWpkVzFsYm5RaU9uc2ljSFZpYkdsalgydGxlWE1pT2x0N0ltbGtJam9pYzJsblh6TTNNemRtTWpJMklpd2lkSGx3WlNJNklrVmpaSE5oVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T1NJc0ltcDNheUk2ZXlKcmRIa2lPaUpGUXlJc0ltTnlkaUk2SW5ObFkzQXlOVFpyTVNJc0luZ2lPaUpzY2s1UFkxOHliM1JqTTNaVWVFbHVaVEJsY2toVVV5MUdiR0Y0T1VKclEyRldla0pMVkZWZloySm5JaXdpZVNJNkluVlVORmQ0Ym5sUllWcDNMVFI2VGxoQmRUTTBkbGxxU25aM1pGaHVaREo0V2pkeFZtVmhhbWQwYzBVaWZTd2ljSFZ5Y0c5elpTSTZXeUpoZFhSb0lpd2laMlZ1WlhKaGJDSmRmVjE5ZlYxOSIsImlzc3VhbmNlRGF0ZSI6IjIwMjAtMTAtMTRUMTk6NTA6MTYuNzEwWiIsInR5cGUiOlsiVmVyaWZpYWJsZUNyZWRlbnRpYWwiLCJEb21haW5MaW5rYWdlQ3JlZGVudGlhbCJdLCJjcmVkZW50aWFsU3ViamVjdCI6eyJpZCI6ImRpZDppb246RWlCN0otazBmM0ltRy16eUZVRlJtVjRnYU9zZGZqeTNrZGRKX2Q5T092N3NoQT8taW9uLWluaXRpYWwtc3RhdGU9ZXlKa1pXeDBZVjlvWVhOb0lqb2lSV2xDWmxCVVdqRlliVFI0VG1NNU5uWlFjbmR2V1RBMWJreGZORFZaVUcxNWIxVm5iM2xTYWtGTFFVMWtaeUlzSW5KbFkyOTJaWEo1WDJOdmJXMXBkRzFsYm5RaU9pSkZhVUZGVGswMGNFNUxZa1o2VTJZeFJrMVlSRTVzWVRCVk5sRkNWRzFyWTFCSE9FMXdUR05SWkZaUFRrdG5JbjAuZXlKMWNHUmhkR1ZmWTI5dGJXbDBiV1Z1ZENJNklrVnBRaTFEVFRVMVNGOTJOMHhhUVc5Q1dFazJPWFpFY25BMVgyZDBYMnB0UXpKdWJsaENMVlU1VGxSeFYyY2lMQ0p3WVhSamFHVnpJanBiZXlKaFkzUnBiMjRpT2lKeVpYQnNZV05sSWl3aVpHOWpkVzFsYm5RaU9uc2ljSFZpYkdsalgydGxlWE1pT2x0N0ltbGtJam9pYzJsblh6TTNNemRtTWpJMklpd2lkSGx3WlNJNklrVmpaSE5oVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T1NJc0ltcDNheUk2ZXlKcmRIa2lPaUpGUXlJc0ltTnlkaUk2SW5ObFkzQXlOVFpyTVNJc0luZ2lPaUpzY2s1UFkxOHliM1JqTTNaVWVFbHVaVEJsY2toVVV5MUdiR0Y0T1VKclEyRldla0pMVkZWZloySm5JaXdpZVNJNkluVlVORmQ0Ym5sUllWcDNMVFI2VGxoQmRUTTBkbGxxU25aM1pGaHVaREo0V2pkeFZtVmhhbWQwYzBVaWZTd2ljSFZ5Y0c5elpTSTZXeUpoZFhSb0lpd2laMlZ1WlhKaGJDSmRmVjE5ZlYxOSIsIm9yaWdpbiI6Ind3dy5nb29nbGUuY29tIn19fQ.xBd3Q-vka4bkaVBtUjLbimzh1HpzqxWxMaF8eD9l_A56N-81FmOXR-VuEdYmqnTE_TxRwFlDXtaWm_QOXgQXrw"
    ]
}"""
        val rpDid = "did:ion:EiB7J-k0f3ImG-zyFUFRmV4gaOsdfjy3kddJ_d9OOv7shA?-ion-initial-state=eyJkZWx0YV9oYXNoIjoiRWlCZlBUWjFYbTR4TmM5NnZQcndvWTA1bkxfNDVZUG15b1Vnb3lSakFLQU1kZyIsInJlY292ZXJ5X2NvbW1pdG1lbnQiOiJFaUFFTk00cE5LYkZ6U2YxRk1YRE5sYTBVNlFCVG1rY1BHOE1wTGNRZFZPTktnIn0.eyJ1cGRhdGVfY29tbWl0bWVudCI6IkVpQi1DTTU1SF92N0xaQW9CWEk2OXZEcnA1X2d0X2ptQzJublhCLVU5TlRxV2ciLCJwYXRjaGVzIjpbeyJhY3Rpb24iOiJyZXBsYWNlIiwiZG9jdW1lbnQiOnsicHVibGljX2tleXMiOlt7ImlkIjoic2lnXzM3MzdmMjI2IiwidHlwZSI6IkVjZHNhU2VjcDI1NmsxVmVyaWZpY2F0aW9uS2V5MjAxOSIsImp3ayI6eyJrdHkiOiJFQyIsImNydiI6InNlY3AyNTZrMSIsIngiOiJsck5PY18yb3RjM3ZUeEluZTBlckhUUy1GbGF4OUJrQ2FWekJLVFVfZ2JnIiwieSI6InVUNFd4bnlRYVp3LTR6TlhBdTM0dllqSnZ3ZFhuZDJ4WjdxVmVhamd0c0UifSwicHVycG9zZSI6WyJhdXRoIiwiZ2VuZXJhbCJdfV19fV19"
        val response = serializer.parse(DnsBindingResponse.serializer(), docJwt)
        val domainLinkageCredentialJwt = response.linkedDids.first()
        coEvery { mockedJwtValidator.verifySignature(any()) } returns true
        runBlocking {
            val validated = jwtDomainLinkageCredentialValidator.validate(domainLinkageCredentialJwt, rpDid, "www.google.com")
            Assertions.assertThat(validated).isTrue()
        }
    }
}