<#import "parts/common.ftl" as c>
<#import  "parts/comment.ftl" as com>
<#include "parts/security.ftl">
<#import  "parts/editMovie.ftl" as edit>

<@c.page>
    <form method="post" action="update" enctype="multipart/form-data">
        <div class="form-group">
            <div class="col-md-3 ">
                <label>Write the name</label>
            </div>
            <div class="form-group col-md-6">
                <input type="text" class="form-control ${(titleError??)?string('is-invalid', '')}"
                       value="<#if film??>${film.title}</#if>" name="title" placeholder="Name film"/>
                <#if titleError??>
                    <div class="invalid-feedback">
                        ${titleError!}
                    </div>
                </#if>
                <div class="form-group col-md-6">
                    <i>Genres: </i>
                    <#list ["HORROR", "COMEDY", "THRILLER", "DOCUMENTARY", "DRAMA", "ADVENTURE", "WESTERN", "MUSICAL", "FANTASY"] as genre>
                        <div class="form-check form-check-inline">
                            <label>
                                <input type="checkbox" value="" name="${genre}">
                                ${genre!}
                            </label>
                        </div>
                    </#list>
                </div>
                <div class="form-group col-md-6">
                    <div>
                        <label>Insert a picture</label>
                    </div>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="validatedCustomFile"
                               name="file"/>
                        <label class="custom-file-label"
                               for="validatedCustomFile"><#if film??>${film.filename!}</#if></label>
                    </div>
                </div>
                <div class="form-group col-md-6">
                <textarea maxlength="500" class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                          name="description"
                          placeholder="description film"><#if film??>${film.description}</#if></textarea>
                    <#if descriptionError??>
                        <div class="invalid-feedback">
                            ${descriptionError!}
                        </div>
                    </#if>
                </div>
                <div class="form-group col-md-6">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input type="hidden" name="id" value="<#if film??>${film.getId()}</#if>"/>
                    <div class="form-group col-md-6">
                        <button type="submit" class="btn btn-primary">Save film</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</@c.page>