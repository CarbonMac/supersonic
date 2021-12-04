package personal.opensrcerer.messaging.impl.paginatedEmbeds.search

import net.dv8tion.jda.api.entities.MessageEmbed
import personal.opensrcerer.launch.SupersonicConstants
import personal.opensrcerer.messaging.interfaces.embedInterfaces.Paginated
import personal.opensrcerer.messaging.entities.EmbedEntity
import personal.opensrcerer.messaging.entities.Page
import personal.opensrcerer.responses.search.Result3
import java.util.*

class SearchEmbed(results: List<SearchEmbedResult>) : Paginated<MessageEmbed> {
    private var currentType: SearchEmbedType = SupersonicConstants.DEFAULT_SEARCH_EMBED_TYPE
    private val map: EnumMap<SearchEmbedType, SearchEmbedResult> = EnumMap(SearchEmbedType::class.java)

    init {
        results.forEach { e -> map[e.type] = e }
    }

    constructor(result: Result3) : this(
        listOf(
            SearchEmbedResult(SearchEmbedType.ALBUM, result.getAlbums() ?: emptyArray<EmbedEntity>()),
            SearchEmbedResult(SearchEmbedType.ARTIST, result.getArtists() ?: emptyArray<EmbedEntity>()),
            SearchEmbedResult(SearchEmbedType.SONG,
                result.getSongs()?.filter { s -> s.isVideo != "true" }?.toTypedArray() ?:
                emptyArray<EmbedEntity>()
            )
        )
    )

    fun type(type: SearchEmbedType) {
        currentType = type
    }

    fun currentType(): SearchEmbedType {
        return currentType
    }

    override fun getPage(pageNumber: Int): MessageEmbed {
        return map[currentType]?.getPage(pageNumber)!!.asMessageEmbed()
    }

    override fun previous(skip: Int): MessageEmbed {
        return map[currentType]?.previous(skip)!!.asMessageEmbed()
    }

    override fun previous(): MessageEmbed {
        return map[currentType]?.previous()!!.asMessageEmbed()
    }

    override fun current(): MessageEmbed {
        return map[currentType]?.current()!!.asMessageEmbed()
    }

    override fun next(): MessageEmbed {
        return map[currentType]?.next()!!.asMessageEmbed()
    }

    override fun next(skip: Int): MessageEmbed {
        return map[currentType]?.next(skip)!!.asMessageEmbed()
    }
}