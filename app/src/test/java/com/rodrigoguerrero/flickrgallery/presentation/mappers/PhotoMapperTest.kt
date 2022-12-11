package com.rodrigoguerrero.flickrgallery.presentation.mappers

import com.rodrigoguerrero.flickrgallery.data.network.dto.PhotoDto
import org.junit.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class PhotoMapperTest {

    @Test
    fun `When photo dto is mapped, then photo ui model has correct values`() {
        val dto = PhotoDto(id = "id", secret = "secret", server = "server", title = "title")

        val result = dto.mapToUi()

        expect {
            that(result.id).isEqualTo("id")
            that(result.title).isEqualTo("title")
            that(result.url).isEqualTo("https://live.staticflickr.com/server/id_secret_b.jpg")
        }
    }
}
