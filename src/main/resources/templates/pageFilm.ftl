<#import "parts/common.ftl" as c>
<#import  "parts/comment.ftl" as com>
<#include "parts/security.ftl">


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
                    <#if mean_rating!=0>
                        User rating of this movie:
                        ${mean_rating!}
                    <#else >
                        you can be the first to rate.
                    </#if>
                </p>
            </div>
            <div class="col-md-8">
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
                            <div class="col-md-1">
                                <#if comment.rating.value??>
                                    ${comment.rating.value!}
                                <#else >
                                    0
                                </#if>
                                ${comment.user.username}
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