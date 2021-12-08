<#import "parts/common.ftl" as c>
<#import "parts/card.ftl" as card>
<#import "addFilm.ftl" as f>


<@c.page>
    <div class="card-columns">
        <#list films as film>
            <div class="card my-3">
                <a href="${film.getAlias()}">
                    <#if film.filename??>
                        <img src="/img/${film.filename}" class="card-img-top">
                    </#if>
                </a>
                <div class="m-2">
                    <span>Name:</span>
                    <i>${film.title}</i>
                    <p>User rating of this movie:
                        <#if film.getRating()?size != 0>
                            <#assign total = 0>
                            <#list film.rating as item>
                                <#assign total += item.value>
                            </#list>
                            ${total / film.getRating()?size}
                        <#else >
                            no rating
                        </#if>
                    </p>
                    <div>
                        <span>Genres:</span>
                        <#list film.genres as genre>${genre!}<#sep>, </#list>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@c.page>