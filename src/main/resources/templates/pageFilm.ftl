<#import "parts/common.ftl" as c>
<#import  "parts/comment.ftl" as com>
<#include "parts/security.ftl">
<#import  "parts/editMovie.ftl" as edit>

<@c.page>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <#if film.filename??>
                    <img src="/img/${film.filename}" class="img-fluid">
                </#if>
                <p>
                    This page was visited:
                    <#if film.view??>
                        ${film.view!}
                    <#else >
                        0
                    </#if>
                    by people
                </p>
                <p>
                    <#if film.getRating()?size != 0>
                        <#assign total = 0>
                        <#list film.rating as item>
                            <#assign total += item.value>
                        </#list>
                        User rating of this movie:
                        ${total / film.getRating()?size}
                    <#else >
                        you can be the first to rate.
                    </#if>
                </p>
            </div>
            <div class="col-md-8">
                <@edit.editMovie film.title film.author.getId()>
                </@>
                <@edit.deleteFilm film.title>

                </@>
                <div class="m-2">
                    <h1>
                        ${film.title}
                    </h1>
                    <div class="pb-3">
                        <ul class="list-group d-inline-flex flex-row">
                            <#list film.genres as genre>
                                <li class="list-group-item">${genre!}</li>
                            </#list>
                        </ul>
                    </div>
                    <div>
                        <p>${film.description}</p>
                    </div>
                    <h3>
                        Reviews and comments
                    </h3>
                    <#list film.comments as comment>
                        <div class="col-md-12 pb-3">
                            <div>
                                <nobr>
                                    ${comment.rating.value}
                                    ${comment.user.username}:
                                </nobr>
                            </div>
                            <div>
                                ${comment.comment}
                            </div>
                        </div>
                    </#list>
                    <div class="list-group">
                        <#if user??>
                            <@com.comment film></@>
                        <#else >
                            <h5>
                                If you want to write a comment, please sign in or register.
                            </h5>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@c.page>